package ru.nsu.dbb.view

import javafx.beans.property.SimpleStringProperty
import javafx.fxml.FXML
import javafx.scene.control.TreeTableColumn
import javafx.scene.control.TreeTableView
import javafx.scene.layout.VBox
import javafx.util.Callback
import ru.nsu.dbb.entity.explain_plan.StringTreeNode
import ru.nsu.dbb.response.ExplainPlanResultPipe
import ru.nsu.dbb.view.represent.ExplainPlanOutputToTree
import ru.nucodelabs.kfx.core.AbstractViewController
import java.net.URL
import java.util.*
import javax.inject.Inject

class OutputViewController @Inject constructor(
    private val explainPlanResultPipe: ExplainPlanResultPipe,
    private val explainPlanOutputToTree: ExplainPlanOutputToTree
) : AbstractViewController<VBox>() {

    @FXML
    private lateinit var outputBox: VBox

    override fun initialize(location: URL, resources: ResourceBundle) {
        super.initialize(location, resources)
        explainPlanResultPipe.observable.addListener { _, _, response ->
            displayExplainResult(response)
        }
    }

    private fun displayExplainResult(result: StringTreeNode) {
        val treeItem = explainPlanOutputToTree.convert(result)
        val treeTableView = TreeTableView(treeItem).apply {
            maxWidth = Double.MAX_VALUE
            columns.addAll(
                result.columnNames.mapIndexed { idx, it ->
                    TreeTableColumn<StringTreeNode, String>(it).apply {
                        prefWidth = 250.0
                        isReorderable = false
                        isSortable = false
                        isEditable = false
                        cellValueFactory = Callback { f ->
                            SimpleStringProperty(f.value.value.columnValues[idx])
                        }
                    }
                }
            )
        }
        outputBox.children.add(treeTableView)
    }
}