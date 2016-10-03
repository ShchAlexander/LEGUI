package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.border.SimpleLineBorder;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.util.ColorConstants;

import static org.liquidengine.legui.util.Util.cpToStr;

/**
 * Created by Shcherbin Alexander on 9/27/2016.
 */
public class Widget extends ComponentContainer {
    private static final String CLOSE_ICON = cpToStr(0xE5CD);

    protected boolean resizable = false;

    protected ComponentContainer container;
    protected Label title;
    protected Button closeButton;

    public Widget() {
        initialize("Widget");
    }

    public Widget(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize("Widget");
    }

    public Widget(Vector2f position, Vector2f size) {
        super(position, size);
        initialize("Widget");
    }

    public Widget(String title) {
        initialize(title);
    }

    public Widget(String title, float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize(title);
    }

    public Widget(String title, Vector2f position, Vector2f size) {
        super(position, size);
        initialize(title);
    }

    private void initialize(String title) {
        SimpleLineBorder simpleLineBorder = new SimpleLineBorder(this, ColorConstants.black(), 1);
        this.title = new Label(title);
        this.title.textState.setHorizontalAlign(HorizontalAlign.LEFT);
        this.title.textState.getPadding().set(10, 5, 10, 5);
        this.title.setBorder(simpleLineBorder);

        this.title.componentListenerHolder.addMouseDragEventListener(event -> {
            if (event.getComponent() == this.title) {
                Vector2f sub = event.getCursorPosition().sub(event.getCursorPositionPrev());
                System.out.println(event);
                position.add(sub);
            }
        });

        this.closeButton = new Button(CLOSE_ICON);
        this.closeButton.backgroundColor.set(1, 0, 0, 1);
        this.closeButton.setBorder(simpleLineBorder);

        this.container = new Panel();
        this.container.setBorder(simpleLineBorder);

        this.backgroundColor.set(1);
        this.border = simpleLineBorder;

        this.addComponent(this.title);
        this.addComponent(this.closeButton);
        this.addComponent(this.container);

        updateWidget();
    }

    private void updateWidget() {
        float titHei = title.size.y;
        if (title.visible) {
            title.position.set(0);
            title.size.x = closeButton.visible ? size.x - titHei : size.x;
            container.position.set(0, titHei);
            container.size.set(size.x, size.y - titHei);
            closeButton.size.set(titHei);
            closeButton.position.set(size.x - titHei, 0);
        } else {
            container.position.set(0, 0);
            container.size.set(size);
            closeButton.size.set(0);
        }
    }

    public float getTitleHeight() {
        return title.size.y;
    }

    public void setTitleHeight(float titleHeight) {
        this.title.size.y = titleHeight;
        updateWidget();
    }

    public boolean isTitleEnabled() {
        return title.isVisible();
    }

    public void setTitleEnabled(boolean titleEnabled) {
        this.title.visible = titleEnabled;
    }

    public boolean isCloseable() {
        return closeButton.isVisible();
    }

    public void setCloseable(boolean closeable) {
        this.closeButton.visible = closeable;
        updateWidget();
    }

    public boolean isResizable() {
        return resizable;
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }

    public Vector4f getTitleBackgroundColor() {
        return title.backgroundColor;
    }

    public void setTitleBackgroundColor(Vector4f titleBackgroundColor) {
        this.title.backgroundColor = titleBackgroundColor;
    }

    public TextState getTitleTextState() {
        return title.textState;
    }

    public Vector4f getCloseButtonColor() {
        return closeButton.backgroundColor;
    }

    public void setCloseButtonColor(Vector4f closeButtonColor) {
        this.closeButton.backgroundColor = closeButtonColor;
    }

    public ComponentContainer getContainer() {
        return container;
    }
}
