package org.example;

import org.aspect.PersistentOperation;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

public class AppConfig {

    private Integer num;
    private String text;

    public Integer getNum() {
        return num;
    }

    @PersistentOperation
    public void setNum(Integer num) {

        this.num = num;
    }

    public String getText() {
        return text;
    }

    @PersistentOperation
    public void setText(String text) {

        this.text = text;
    }

}
