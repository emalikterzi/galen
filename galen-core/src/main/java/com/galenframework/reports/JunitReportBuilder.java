/*******************************************************************************
* Copyright 2018 Ivan Shubin http://galenframework.com
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
package com.galenframework.reports;

import com.galenframework.utils.GalenUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.galenframework.utils.GalenUtils.makeSureFolderExists;

public class JunitReportBuilder {
    
    private Configuration freemarkerConfiguration = new Configuration();

    private TestIdGenerator testIdGenerator = new TestIdGenerator();

    public void build(List<GalenTestInfo> tests, String reportPath) throws IOException, TemplateException {
        List<GalenTestAggregatedInfo> aggregatedTests = new LinkedList<>();
        
        for (GalenTestInfo test : tests) {
            GalenTestAggregatedInfo aggregatedInfo = new GalenTestAggregatedInfo(testIdGenerator.generateTestId(test.getName()), test);
            aggregatedTests.add(aggregatedInfo);
        }

        exportJunitReport(aggregatedTests, reportPath);
    }

    private void exportJunitReport(List<GalenTestAggregatedInfo> tests, String reportPath) throws IOException, TemplateException {
        File file = new File(reportPath);
        makeSureParentPathExists(file);
        file.createNewFile();
        
        FileWriter fileWriter = new FileWriter(file);
        Map<String, Object> model = new HashMap<>();
        model.put("tests", tests);
        
        Template template = new Template("report-main", new InputStreamReader(getClass().getResourceAsStream("/junit-report/junit-report.ftl.xml")), freemarkerConfiguration);
        template.process(model, fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    private void makeSureParentPathExists(File file) throws IOException {
        File parentDir = file.getParentFile();
        if (parentDir != null) {
            makeSureFolderExists(parentDir);
        }
    }

}
