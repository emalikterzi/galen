/*******************************************************************************
* Copyright 2015 Ivan Shubin http://mindengine.net
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
package net.mindengine.galen.testng;

import java.util.List;

import net.mindengine.galen.support.GalenReportsContainer;
import net.mindengine.galen.config.GalenConfig;
import net.mindengine.galen.config.GalenProperty;
import net.mindengine.galen.reports.GalenTestInfo;
import net.mindengine.galen.reports.HtmlReportBuilder;
import net.mindengine.galen.reports.model.FileTempStorage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

public class GalenTestNgReportesListener implements IReporter {

    private static final Logger LOG = LoggerFactory.getLogger(GalenTestNgReportesListener.class);

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> iSuites, String s) {
        LOG.info("Generating Galen Html reports");
        List<GalenTestInfo> tests = GalenReportsContainer.get().getAllTests();
        try {
            new HtmlReportBuilder().build(tests, GalenConfig.getConfig().readProperty(GalenProperty.TEST_JAVA_REPORT_OUTPUTFOLDER));
            cleanData(tests);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Removes temporary test data
     * 
     * @param testInfos
     */
    private void cleanData(List<GalenTestInfo> testInfos) {
        for (GalenTestInfo testInfo : testInfos) {
            if (testInfo.getReport() != null) {
                try {
                    FileTempStorage storage = testInfo.getReport().getFileStorage();
                    if (storage != null) {
                        storage.cleanup();
                    }
                } catch (Exception e) {
                    LOG.error("Unknown error during report cleaning", e);
                }
            }
        }
    }

}
