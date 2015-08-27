
/*
 * Copyright (c) 2011-2014 Julien Nicoulaud <julien.nicoulaud@gmail.com>
 * Copyright (c) 2015 Vladimir Schneider <vladimir.schneider@gmail.com>
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.vladsch.idea.multimarkdown.settings;

import com.google.common.io.Resources;
import com.intellij.ide.highlighter.HighlighterFactory;
import com.intellij.lang.Language;
import com.intellij.openapi.editor.EditorSettings;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.fileTypes.ex.FileTypeManagerEx;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.vladsch.idea.multimarkdown.editor.MultiMarkdownPreviewEditor;
import org.apache.commons.codec.Charsets;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class MultiMarkdownSettingsPanel {

    public JSpinner parsingTimeoutSpinner;
    public JCheckBox smartsCheckBox;
    public JCheckBox quotesCheckBox;
    public JCheckBox abbreviationsCheckBox;
    public JCheckBox hardWrapsCheckBox;
    public JCheckBox autoLinksCheckBox;
    public JCheckBox wikiLinksCheckBox;
    public JCheckBox tablesCheckBox;
    public JCheckBox definitionsCheckBox;
    public JCheckBox fencedCodeBlocksCheckBox;
    public JCheckBox suppressHTMLBlocksCheckBox;
    public JCheckBox suppressInlineHTMLCheckBox;
    public JCheckBox strikethroughCheckBox;
    public JSpinner updateDelaySpinner;
    public JSpinner maxImgWidthSpinner;
    //public JTextArea textCustomCss;
    public JPanel customCssPanel;
    public JButton btnResetCss;
    public JButton btnLoadDefault;
    public JCheckBox taskListsCheckBox;
    public JCheckBox headerSpaceCheckBox;
    public JCheckBox showHtmlTextCheckBox;
    public JCheckBox showHtmlTextAsModifiedCheckBox;
    public JCheckBox anchorLinksCheckBox;
    public JCheckBox forceListParaCheckBox;
    public JCheckBox relaxedHRulesCheckBox;
    public JComboBox htmlThemeComboBox;
    public JCheckBox enableTrimSpacesCheckBox;
    private JCheckBox todoCommentsCheckBox;

    public JPanel panel;

    // need this so that we dont try to access components before they are created
    public JComponent getComponent(String name) {
        if (name.equals("parsingTimeoutSpinner")) return parsingTimeoutSpinner;
        if (name.equals("smartsCheckBox")) return smartsCheckBox;
        if (name.equals("quotesCheckBox")) return quotesCheckBox;
        if (name.equals("abbreviationsCheckBox")) return abbreviationsCheckBox;
        if (name.equals("hardWrapsCheckBox")) return hardWrapsCheckBox;
        if (name.equals("autoLinksCheckBox")) return autoLinksCheckBox;
        if (name.equals("wikiLinksCheckBox")) return wikiLinksCheckBox;
        if (name.equals("tablesCheckBox")) return tablesCheckBox;
        if (name.equals("definitionsCheckBox")) return definitionsCheckBox;
        if (name.equals("fencedCodeBlocksCheckBox")) return fencedCodeBlocksCheckBox;
        if (name.equals("suppressHTMLBlocksCheckBox")) return suppressHTMLBlocksCheckBox;
        if (name.equals("suppressInlineHTMLCheckBox")) return suppressInlineHTMLCheckBox;
        if (name.equals("strikethroughCheckBox")) return strikethroughCheckBox;
        if (name.equals("updateDelaySpinner")) return updateDelaySpinner;
        if (name.equals("maxImgWidthSpinner")) return maxImgWidthSpinner;
        if (name.equals("textCustomCss")) return textCustomCss;
        if (name.equals("btnResetCss")) return btnResetCss;
        if (name.equals("btnLoadDefault")) return btnLoadDefault;
        if (name.equals("taskListsCheckBox")) return taskListsCheckBox;
        if (name.equals("headerSpaceCheckBox")) return headerSpaceCheckBox;
        if (name.equals("showHtmlTextCheckBox")) return showHtmlTextCheckBox;
        if (name.equals("showHtmlTextAsModifiedCheckBox")) return showHtmlTextAsModifiedCheckBox;
        if (name.equals("anchorLinksCheckBox")) return anchorLinksCheckBox;
        if (name.equals("forceListParaCheckBox")) return forceListParaCheckBox;
        if (name.equals("relaxedHRulesCheckBox")) return relaxedHRulesCheckBox;
        if (name.equals("htmlThemeComboBox")) return htmlThemeComboBox;
        if (name.equals("enableTrimSpacesCheckBox")) return enableTrimSpacesCheckBox;
        //if (name.equals("todoCommentsCheckBox")) return todoCommentsCheckBox;
        return null;
    }

    public JPanel settingsPanel;
    public JPanel extensionsPanel;

    private JLabel suppressInlineHTMLDescriptionLabel;
    private JLabel suppressHTMLBlocksDescriptionLabel;
    private JLabel fencedCodeBlocksDescriptionLabel;
    private JLabel definitionsDescriptionLabel;
    private JLabel tablesDescriptionLabel;
    private JLabel autoLinksDescriptionLabel;
    private JLabel wikiLinksDescriptionLabel;
    private JLabel hardWarpsDescriptionLabel;
    private JLabel abbreviationsDescriptionLabel;
    private JLabel quotesDescriptionLabel;
    private JLabel smartsDescriptionLabel;
    private JLabel strikethroughDescriptionLabel;
    private JLabel parsingTimeoutDescriptionLabel;
    private CustomizableEditorTextField textCustomCss;

    protected void showHtmlTextStateChanged() {
        if (showHtmlTextAsModifiedCheckBox != null) {
            boolean checked = showHtmlTextCheckBox.isSelected();
            showHtmlTextAsModifiedCheckBox.setEnabled(checked);
        }
    }

    public MultiMarkdownSettingsPanel() {
        btnResetCss.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                textCustomCss.setText("");
            }
        });

        btnLoadDefault.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                try {
                    textCustomCss.setText(Resources.toString(MultiMarkdownPreviewEditor.class.getResource(
                            htmlThemeComboBox.getSelectedIndex() == 0
                                    ? MultiMarkdownPreviewEditor.PREVIEW_STYLESHEET_PATH0
                                    : MultiMarkdownPreviewEditor.PREVIEW_STYLESHEET_PATH1), Charsets.UTF_8));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        showHtmlTextCheckBox.addPropertyChangeListener(new PropertyChangeListener() {
            @Override public void propertyChange(PropertyChangeEvent evt) {
                showHtmlTextStateChanged();
            }
        });

        showHtmlTextCheckBox.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                showHtmlTextStateChanged();
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        Language language = Language.findLanguageByID("CSS");
        final boolean foundCSS = language != null;

        final FileType fileType = language != null && language.getAssociatedFileType() != null ? language.getAssociatedFileType() : StdFileTypes.PLAIN_TEXT;

        ProjectManager projectManager = ProjectManager.getInstance();
        Project[] projects = projectManager.getOpenProjects();
        Project project = projects.length > 0 ? projects[0] : null; //projectManager.getDefaultProject();
        textCustomCss = new CustomizableEditorTextField(fileType, project, "", false);
        textCustomCss.setFontInheritedFromLAF(false);
        textCustomCss.registerListener(new CustomizableEditorTextField.EditorCustomizationListener() {
            @Override public boolean editorCreated(@NotNull EditorEx editor, @NotNull Project project) {
                EditorSettings settings = editor.getSettings();
                settings.setRightMarginShown(true);
                //settings.setRightMargin(-1);
                if (foundCSS) settings.setFoldingOutlineShown(true);
                settings.setLineNumbersShown(true);
                if (foundCSS) settings.setLineMarkerAreaShown(true);
                settings.setIndentGuidesShown(true);
                settings.setVirtualSpace(true);
                settings.setLineCursorWidth(2);
                settings.setWheelFontChangeEnabled(false);
                editor.setHorizontalScrollbarVisible(true);
                editor.setVerticalScrollbarVisible(true);

                FileType fileTypeH = FileTypeManagerEx.getInstanceEx().getFileTypeByExtension(".css");
                FileType highlighterFileType = foundCSS ? fileType : fileTypeH;
                if (highlighterFileType != null && project != null) {
                    editor.setHighlighter(HighlighterFactory.createHighlighter(project, highlighterFileType));
                }

                return false;
            }
        });
    }
}