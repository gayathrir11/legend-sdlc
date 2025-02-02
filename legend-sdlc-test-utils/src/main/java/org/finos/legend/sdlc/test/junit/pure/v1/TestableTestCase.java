// Copyright 2022 Goldman Sachs
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License

package org.finos.legend.sdlc.test.junit.pure.v1;

import org.finos.legend.engine.language.pure.compiler.toPureGraph.PureModel;
import org.finos.legend.engine.language.pure.modelManager.ModelManager;
import org.finos.legend.engine.protocol.pure.v1.model.context.PureModelContextData;
import org.finos.legend.engine.shared.core.deployment.DeploymentMode;
import org.finos.legend.engine.testable.TestableRunner;
import org.finos.legend.sdlc.test.junit.LegendSDLCTestCase;

public class TestableTestCase extends LegendSDLCTestCase
{
    private final TestableHelper helper;

    public TestableTestCase(String testablePath, PureModel pureModel, PureModelContextData pureModelContextData)
    {
        super(testablePath);
        this.helper = new TestableHelper(3, testablePath, new TestableRunner(new ModelManager(DeploymentMode.PROD)), pureModel, pureModelContextData);
    }

    @Override
    protected void doRunTest()
    {
        try
        {
            this.helper.runTest();
        }
        catch (Exception e)
        {
            StringBuilder builder = new StringBuilder("Error running test suites");
            String eMessage = e.getMessage();
            if (eMessage != null)
            {
                builder.append(": ").append(eMessage);
            }
            throw new RuntimeException(builder.toString(), e);
        }
    }
}