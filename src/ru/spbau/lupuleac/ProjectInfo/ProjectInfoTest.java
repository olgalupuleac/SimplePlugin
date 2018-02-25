package ru.spbau.lupuleac.ProjectInfo;

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectInfoTest extends LightCodeInsightFixtureTestCase {
    private ProjectInfo projectInfo;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected String getTestDataPath() {
        return "testCode/src";
    }

    public void testTotalNumberOfClasses() {
        myFixture.configureByFiles("MyCode.java", "AdditionalClass.java");
        projectInfo = new ProjectInfo(getProject());
        assertEquals(2, projectInfo.getTotalNumberOfClasses());
    }

    public void testTotalNumberOfMethods() {
        myFixture.configureByFiles("MyCode.java", "AdditionalClass.java");
        projectInfo = new ProjectInfo(getProject());
        assertEquals(2, projectInfo.getTotalNumberOfMethods());
    }

    public void testAverageNumberOfFieldsInClass() {
        myFixture.configureByFiles("MyCode.java", "AdditionalClass.java");
        projectInfo = new ProjectInfo(getProject());
        assertEquals(1.5, projectInfo.getAverageNumberOfFields());
    }

    public void testAverageMethodLength() {
        myFixture.configureByFiles("ClassWithShortMethods.java");
        projectInfo = new ProjectInfo(getProject());
        assertEquals(8.50, projectInfo.getAverageMethodLength());
    }

    public void testAverageFieldNameLength() {
        myFixture.configureByFiles("MyCode.java", "AdditionalClass.java");
        projectInfo = new ProjectInfo(getProject());
        List<Double> averageFieldNameLength = projectInfo.getClassInfoMap().
                values().stream().map(ClassInfo::averageFieldsNamesLength).collect(Collectors.toList());
        assertEquals(2, averageFieldNameLength.size());
        assertTrue(averageFieldNameLength.contains(2.0));
        assertTrue(averageFieldNameLength.contains(1.5));
    }

}