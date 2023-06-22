package ru.nsu.dbb.view

import javafx.application.Platform
import javafx.beans.property.SimpleStringProperty
import javafx.fxml.FXML
import javafx.scene.control.ScrollBar
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TreeTableColumn
import javafx.scene.control.TreeTableView
import javafx.scene.layout.Region
import javafx.scene.layout.VBox
import javafx.util.Callback
import ru.nsu.dbb.controller.ConsoleController
import ru.nsu.dbb.entity.explain_plan.StringTreeNode
import ru.nsu.dbb.entity.select.ResultRow
import ru.nsu.dbb.entity.select.ResultTable
import ru.nsu.dbb.response.ExplainPlanResultPipe
import ru.nsu.dbb.response.SelectResultPipe
import ru.nsu.dbb.view.represent.mapper.ExplainPlanResponseMapper
import ru.nucodelabs.kfx.core.AbstractViewController
import java.net.URL
import java.util.*
import javax.inject.Inject

class OutputViewController @Inject constructor(
    consoleController: ConsoleController,
    private val explainPlanResponseMapper: ExplainPlanResponseMapper
) : AbstractViewController<VBox>() {

    private val explainPlanResultPipe: ExplainPlanResultPipe = consoleController.explainPlanResultPipe
    private val selectResultPipe: SelectResultPipe = consoleController.selectResultPipe

    @FXML
    private lateinit var outputBox: VBox

    override fun initialize(location: URL, resources: ResourceBundle) {
        super.initialize(location, resources)
        explainPlanResultPipe.observable.addListener { _, _, response ->
            displayExplainResult(response)
        }
        selectResultPipe.observable.addListener { _, _, response ->
            displaySelectResult(response)
        }
    }

    private fun displayExplainResult(result: StringTreeNode) {
        val treeItem = explainPlanResponseMapper.mapToTeeView(result)
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
        display(treeTableView)
    }

    private fun displaySelectResult(result: ResultTable) {
        val tableView = TableView(result.rows).apply {
            maxWidth = Double.MAX_VALUE
            columns.addAll(
                result.columns.mapIndexed { idx, column ->
                    TableColumn<ResultRow, String>(column.label + "\n" + column.typeName).apply {
                        prefWidth = 250.0
                        isReorderable = false
                        isSortable = false
                        isEditable = false
                        cellValueFactory = Callback { f ->
                            SimpleStringProperty(f.value.get(idx)?.toString() ?: "[null]")
                        }
                    }
                }
            )
        }

        Platform.runLater {
            val scrollBar = tableView.lookup(".scroll-bar:vertical") as ScrollBar?
            scrollBar?.let {
                it.valueProperty().addListener { _, _, newValue ->
                    if (newValue == 1.0) {
                        selectResultPipe.loadMoreRows()
                    }
                }
            }
        }

        display(tableView)
    }

    private fun display(region: Region) {
        region.prefHeightProperty().bind(outputBox.heightProperty())
        outputBox.children.clear()
        outputBox.children.add(region)
    }
}