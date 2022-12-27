package ru.nsu.dbb.explain_plan

import com.github.javaparser.utils.Log
import ru.nsu.dbb.entity.explain_plan.Operation
import ru.nsu.dbb.entity.explain_plan.TreeNode
import java.sql.ResultSet

class ExplainPlanParser {
}

fun explainResultSetToTree(rs: ResultSet, queryName: String = "Select", dialect: SQLDialect): TreeNode {
    val root = TreeNode(
        Operation(
            name = queryName, params = "", rows = null, startupCost = null, totalCost = null, desc = ""
        ), mutableListOf(), null
    )
    when (dialect) {
        SQLDialect.POSTGRE -> {
            val hm = HashMap<Int, TreeNode>()
            var subRoot = root
            hm[-1] = subRoot
            while (rs.next()) {
                val str = rs.getString(1)
                val spacesCnt = explainPostresTabulationCounter(str)
                val result = explainPostresRegexParser(str)
                if (result.size == 6) {
                    val operation = Operation(
                        result[0],
                        result[1],
                        result[2].toIntOrNull(),
                        result[3].toDoubleOrNull(),
                        result[4].toDoubleOrNull(),
                        result[5]
                    )
                    if (hm.containsKey(spacesCnt)) {
                        val node = TreeNode(operation, arrayListOf(), hm[spacesCnt]!!.parent)
                        hm[spacesCnt]!!.childNodes.lastOrNull()?.parent = node
                        hm[spacesCnt]!!.parent?.childNodes?.add(node)
                        hm[spacesCnt] = node
                    } else {
                        val node = TreeNode(operation, mutableListOf(), subRoot)
                        hm[spacesCnt] = node
                        hm[spacesCnt]!!.parent!!.childNodes.add(node)
                    }
                    subRoot = hm[spacesCnt]!!
                } else { //additional info to node
                    subRoot.operation.desc = result[0] + "; " + subRoot.operation.desc
                }
            }
        }
        SQLDialect.MYSQL -> {

        }
        SQLDialect.SQLITE -> {

        }
    }

    return root
}

fun explainPostresTabulationCounter(str: String): Int {
    var cnt = 0
    for (ch in str) {
        if (ch == ' ') {
            cnt++
        } else {
            return cnt
        }
    }
    return cnt
}

fun explainLiteRegexParser(str: String): List<String> {
//    var regex = """([.[^a-z]]+)([a-z\s]*)""".toRegex()
//    var matchResult = regex.find(str)

    val regexOrder = "(?)(ORDER BY)".toRegex()
    if (regexOrder.containsMatchIn(str)) {
        return arrayListOf("Order By", "", str)
    }

    val regexGroup = "(?)(GROUP BY)".toRegex()
    if (regexGroup.containsMatchIn(str)) {
        return arrayListOf("Group By", "", str)
    }

    if (str == "LEFT" || str == "RIGHT") {
        return arrayListOf(str, "", str)
    }

    val regexFilter1 = "(?i)(FILTER ON) ([\\w\\d]+\\s[(].+[)])".toRegex()
    val mrFilter1 = regexFilter1.find(str)

    val regexFilter2 = "(?i)(FILTER ON) ([\\w\\d]+)".toRegex()
    val mrFilter2 = regexFilter2.find(str)

    val mrFilter = mrFilter1 ?: mrFilter2

    if (mrFilter != null) {
        val (_, pFilter) = mrFilter.destructured
        return arrayListOf("Filter on", pFilter, str)
    }

    val regexScan = "(?i)(SCAN) ([\\w\\d]+)".toRegex()
    val mrScan = regexScan.find(str)

    if (mrScan != null) {
        val (_, pScan) = mrScan.destructured
        return arrayListOf("Full Scan", "table: $pScan", str)
    }

    var params = ""

    val regexIndex1 = "(?i)(INDEX) ([\\w\\d]+\\s[(].+[)])".toRegex()
    val mrIndex1 = regexIndex1.find(str)

    val regexIndex2 = "(?i)(INDEX) ([\\w\\d]+)".toRegex()
    val mrIndex2 = regexIndex2.find(str)

    val mrIndex = mrIndex1 ?: mrIndex2

    if (mrIndex != null) {
        val (_, pIndex) = mrIndex.destructured
        params += "$pIndex; "
    }

    val regexSearch = "(?i)(SEARCH) ([\\w\\d]+)".toRegex()
    val mrSearch = regexSearch.find(str)

    if (mrSearch != null) {
        val (_, pSearch) = mrSearch.destructured
        params += "$pSearch ;"
        return arrayListOf("Index Scan", params, str)
    }

    Log.error("explainLiteRegexParser")
    return arrayListOf("Undefined", "", "")
}

fun explainPostresRegexParser(str: String): List<String> {
    var regex = """\s*(.+)\s{2}[(]cost=(\d+[.]\d+)[.]{2}(\d+[.]\d+) rows=(\d+) width=(\d+)[)].*""".toRegex()
    var matchResult = regex.find(str)
    if (matchResult != null) {
        val (name_params, startupCost, totalCost, rows, width) = matchResult.destructured

        val np = name_params.split(" on ")
        var name = np[0]
        var params = ""

        if (np.size == 2) {
            params = np[1]
        }
        name = name.removePrefix("->  ")
        println("$name; $params; $rows; $startupCost; $totalCost; $width")
        return arrayListOf(name, params, rows, startupCost, totalCost, "Plan Width = " + width)
    } else {
        regex = """\s*(\w[\w\s]*): (.*)""".toRegex()
        matchResult = regex.find(str)
        if (matchResult != null) {
            val (rd1, rd2) = matchResult.destructured
            val clrStr = "$rd1 = $rd2"
            println(clrStr)
            return arrayListOf(clrStr)
        }
    }
    Log.error("explainPostresRegexParser")
    return arrayListOf("")
}

enum class SQLDialect {
    POSTGRE,
    MYSQL,
    SQLITE
}