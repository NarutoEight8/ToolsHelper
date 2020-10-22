package com.nathan96169.toolshelper.dispatcher;


public class ParamEvent
{
  public Object param;
  public String eventName = "nnnn";

  public ParamEvent(String eventName, Object parameter) {
    this.eventName = eventName;
    this.param = parameter;
  }
  public ParamEvent(String eventName) {
    this.eventName = eventName;
    this.param = null;
  }

}