package com.codeaim.urlcheck.common.model;

import org.junit.Assert;
import org.junit.Test;

public class ResultTest
{
    @Test
    public void create()
    {
        Result newResult = Result.builder().build();

        Assert.assertNotNull(newResult);
    }

    @Test
    public void toStringResult()
    {
        System.out.println(Result.builder()
                .probe("testProbe")
                .changed(true)
                .confirmation(true)
                .check(Check.builder().build())
                .previous(Result.builder().build())
                .responseTime(15)
                .status(Status.UP)
                .statusCode(200)
                .build().toString());
    }
}
