/*******************************************************************************
* Copyright 2013 Ivan Shubin http://mindengine.net
* 
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* 
*   http://www.apache.org/licenses/LICENSE-2.0
* 
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
******************************************************************************/
package net.mindengine.galen.components;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import com.google.common.io.Files;

import net.mindengine.galen.browser.Browser;
import net.mindengine.galen.page.Page;

public class MockedBrowser implements Browser {

    private String url;
    private Dimension screenSize;

    public MockedBrowser(String url, Dimension screenSize) {
        this.url = url;
        this.screenSize = screenSize;
    }

    @Override
    public void quit() {
    }

    @Override
    public void changeWindowSize(Dimension screenSize) {
    }

    @Override
    public void load(String url) {
    }

    @Override
    public void executeJavascript(String javascript) {
    }

    @Override
    public Page getPage() {
        return null;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public Dimension getScreenSize() {
        return this.screenSize;
    }

    @Override
    public String createScreenshot() {
        File tempDir = Files.createTempDir();
        
        File file = new File(tempDir.getAbsolutePath() + UUID.randomUUID().toString() + ".png");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

}