package com.spaceApplication.client.exception;

import java.io.Serializable;

/**
 * Created by Кристина on 28.02.2016.
 */
public class SpaceModelException extends  Exception implements Serializable {
    private String explanation;

    public SpaceModelException() {
    }

    public SpaceModelException(String symbol) {
        this.explanation = symbol;
    }

    public String getExplanation() {
        return this.explanation;
    }
}
