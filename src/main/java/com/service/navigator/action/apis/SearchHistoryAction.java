package com.service.navigator.action.apis;

import com.intellij.icons.AllIcons;
import com.intellij.ide.IdeBundle;
import com.intellij.ide.actions.GotoActionBase;
import com.intellij.ide.util.ElementsChooser;
import com.intellij.ide.util.gotoByName.ChooseByNameItemProvider;
import com.intellij.ide.util.gotoByName.ChooseByNamePopup;
import com.intellij.ide.util.treeView.AbstractTreeStructure;
import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.actionSystem.ex.CheckboxAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.JBPopupListener;
import com.intellij.openapi.ui.popup.LightweightWindowEvent;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.tree.AsyncTreeModel;
import com.intellij.ui.tree.StructureTreeModel;
import com.intellij.ui.treeStructure.SimpleTree;
import com.intellij.ui.treeStructure.SimpleTreeStructure;
import com.intellij.util.ui.CheckBox;
import com.service.navigator.action.MyCheckBoxAction;
import com.service.navigator.action.TextFieldAction;
import com.service.navigator.action.ui.history.HistoryRootNode;
import com.service.navigator.action.ui.search.GotoRequestContributor;
import com.service.navigator.action.ui.search.GotoRequestProvider;
import com.service.navigator.action.ui.search.RequestFilteringGotoByModel;
import com.service.navigator.action.ui.search.RestSearchItem;
import com.service.navigator.constant.HttpMethod;
import com.service.navigator.constant.Icons;
import com.service.navigator.view.model.apis.RootNode;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchHistoryAction extends ToggleAction implements Disposable {

    private JBPopup myHistoryPopup;

    private SimpleTree historyTree;

    private StructureTreeModel<AbstractTreeStructure> treeModel;

    private final HistoryRootNode rootNode;

    private final TextFieldAction textFieldAction;

    private final MyCheckBoxAction checkboxAction;

    public SearchHistoryAction() {
        super("Search History", "", AllIcons.Vcs.History);
        rootNode = new HistoryRootNode(null);

        treeModel = new StructureTreeModel<>(new SimpleTreeStructure() {
            @Override
            public @NotNull Object getRootElement() {
                return rootNode;
            }
        }, null, this);
        historyTree = new SimpleTree(new AsyncTreeModel(treeModel, this));
        historyTree.setRootVisible(true);
        historyTree.setShowsRootHandles(true);
        historyTree.getEmptyText().clear();
        historyTree.setBorder(BorderFactory.createEmptyBorder());
        historyTree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

        textFieldAction = new TextFieldAction("", "", Icons.Node.TreeRootNode, 10);
        checkboxAction = new MyCheckBoxAction();
    }

    @Override
    public boolean isSelected(@NotNull AnActionEvent anActionEvent) {
        return myHistoryPopup != null && !myHistoryPopup.isDisposed();
    }

    @Override
    public void setSelected(@NotNull AnActionEvent anActionEvent, boolean state) {
        if (state) {
            showPopup(anActionEvent);
        } else {
            if (myHistoryPopup != null && !myHistoryPopup.isDisposed()) {
                myHistoryPopup.cancel();
            }
        }
    }

    private void showPopup(AnActionEvent e) {
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        if (myHistoryPopup != null) {
            return;
        }
        JBPopupListener popupCloseListener = new JBPopupListener() {
            @Override
            public void onClosed(@NotNull LightweightWindowEvent event) {
                myHistoryPopup = null;
            }
        };
        myHistoryPopup = JBPopupFactory.getInstance()
                .createComponentPopupBuilder(createHistoryTree(project), null)
                .setModalContext(false)
                .setFocusable(true)
                .setRequestFocus(true)
                .setResizable(true)
                .setCancelOnClickOutside(true)
                .setMinSize(new Dimension(200, 200))
//                                      .setDimensionServiceKey(project, getDimensionServiceKey(), false)
                .addListener(popupCloseListener)
                .createPopup();
        Component anchor = e.getInputEvent().getComponent();
        if (anchor.isValid()) {
            myHistoryPopup.showUnderneathOf(anchor);
        } else {
            Component component = e.getData(PlatformDataKeys.CONTEXT_COMPONENT);
            if (component != null) {
                myHistoryPopup.showUnderneathOf(component);
            } else {
                myHistoryPopup.showInFocusCenter();
            }
        }
    }

    private JComponent createHistoryTree(Project project) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(ScrollPaneFactory.createScrollPane(historyTree));
        JPanel buttons = new JPanel();
        buttons.add(checkboxAction.createCustomComponent(Presentation.newTemplatePresentation(), ""));
        buttons.add(textFieldAction.createCustomComponent(Presentation.newTemplatePresentation(), ""));
        panel.add(buttons);
        return panel;
    }

    @Override
    public void dispose() {

    }
}
