/*
 * Copyright (c) 2015-2015 Vladimir Schneider <vladimir.schneider@gmail.com>
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
package com.vladsch.idea.multimarkdown.psi;

import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;

public interface MultiMarkdownWikiPageRef extends MultiMarkdownNamedElement {
    String getFileName();

    @Override
    String getName();

    @Override
    // this one will only change the name part, not the path part of the link
    PsiElement setName(String newName);

    // this one will preserve the path and only change the name unless fileMoved is true
    PsiElement setName(String newName, boolean fileMoved);

    @Override
    PsiElement getNameIdentifier();

    ItemPresentation getPresentation();
}