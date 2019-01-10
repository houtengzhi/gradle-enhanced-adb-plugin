package com.yechy.gradleplugin.adb

class CustomTaskExt {
    final String name
    String command
    String scriptPath
    String executeProcedure
    String group
    String description

    CustomTaskExt(String name) {
        this.name = name
    }


    @Override
    public String toString() {
        return "CustomTaskExt{" +
                "name='" + name + '\'' +
                ", command='" + command + '\'' +
                ", scriptPath='" + scriptPath + '\'' +
                ", executeProcedure='" + executeProcedure + '\'' +
                ", group='" + group + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}