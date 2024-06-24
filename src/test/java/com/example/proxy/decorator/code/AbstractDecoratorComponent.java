package com.example.proxy.decorator.code;

public abstract class AbstractDecoratorComponent implements Component {

  protected final Component component;

  public AbstractDecoratorComponent(Component component) {
    this.component = component;
  }

  @Override
  public String operation() {
    return component.operation();
  }
}
