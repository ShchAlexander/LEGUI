package org.liquidengine.legui.component;

import org.joml.Vector2f;
import org.liquidengine.legui.component.border.SimpleLineBorder;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.util.ColorConstants;

/**
 * Created by Alexander on 09.10.2016.
 */
public class ScrollablePanel extends ComponentContainer implements Viewport {
    protected ScrollBar verticalScrollBar;
    protected ScrollBar horizontalScrollBar;
    protected ComponentContainer viewport;
    protected ComponentContainer container;
    public static final float INITIAL_SCROLL_SIZE = 12f;

    public ScrollablePanel() {
        this(10, 10, 100, 100);
    }

    public ScrollablePanel(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize(x, y, width, height);
    }

    public ScrollablePanel(Vector2f position, Vector2f size) {
        super(position, size);
        initialize(position.x, position.y, size.x, size.y);
    }

    private void initialize(float x, float y, float width, float height) {
        float viewportWidth = width - INITIAL_SCROLL_SIZE;
        float viewportHeight = height - INITIAL_SCROLL_SIZE;

        verticalScrollBar = new ScrollBar();
        verticalScrollBar.setBackgroundColor(ColorConstants.darkGray());
        verticalScrollBar.setPosition(viewportWidth, 0);
        verticalScrollBar.setSize(INITIAL_SCROLL_SIZE, viewportHeight);
        verticalScrollBar.setArrowColor(ColorConstants.white());
        verticalScrollBar.setScrollColor(ColorConstants.white());
        verticalScrollBar.setArrowsEnabled(true);
        verticalScrollBar.setOrientation(Orientation.VERTICAL);
        verticalScrollBar.setBorder(new SimpleLineBorder(verticalScrollBar, ColorConstants.darkGray(), 1));
        verticalScrollBar.setArrowSize(INITIAL_SCROLL_SIZE);
        verticalScrollBar.setViewport(this);

        horizontalScrollBar = new ScrollBar();
        horizontalScrollBar.setBackgroundColor(ColorConstants.darkGray());
        horizontalScrollBar.setPosition(0, viewportHeight);
        horizontalScrollBar.setSize(viewportWidth, INITIAL_SCROLL_SIZE);
        horizontalScrollBar.setArrowColor(ColorConstants.white());
        horizontalScrollBar.setScrollColor(ColorConstants.white());
        horizontalScrollBar.setArrowsEnabled(true);
        horizontalScrollBar.setOrientation(Orientation.HORIZONTAL);
        horizontalScrollBar.setBorder(new SimpleLineBorder(horizontalScrollBar, ColorConstants.darkGray(), 1));
        horizontalScrollBar.setArrowSize(INITIAL_SCROLL_SIZE);
        horizontalScrollBar.setViewport(this);

        viewport = new Panel(0, 0, viewportWidth, viewportHeight);
        viewport.setBackgroundColor(1, 1, 1, 0);
        viewport.setBorder(new SimpleLineBorder(viewport, ColorConstants.darkGray(), 1));

        container = new Panel(0, 0, viewportHeight, viewportWidth);
        viewport.addComponent(container);

        this.addComponent(verticalScrollBar);
        this.addComponent(horizontalScrollBar);
        this.addComponent(viewport);

        resize();
    }

    public void resize() {
        boolean horizontalScrollBarVisible = horizontalScrollBar.isVisible();
        boolean verticalScrollBarVisible = verticalScrollBar.isVisible();

        Vector2f scrollablePanelSize = new Vector2f(size);
        Vector2f containerSize = new Vector2f(container.size);
        Vector2f viewportSize = new Vector2f(size);

        if (horizontalScrollBarVisible) viewportSize.y = scrollablePanelSize.y - horizontalScrollBar.size.y;
        if (verticalScrollBarVisible) viewportSize.x = scrollablePanelSize.x - verticalScrollBar.size.x;
        viewport.size = viewportSize;

        float horizontalRange = horizontalScrollBar.getMaxValue() - horizontalScrollBar.getMinValue();
        horizontalScrollBar.setVisibleAmount(containerSize.x >= viewportSize.x ? (horizontalRange * viewportSize.x / containerSize.x) : horizontalRange);

        float verticalRange = verticalScrollBar.getMaxValue() - verticalScrollBar.getMinValue();
        verticalScrollBar.setVisibleAmount(containerSize.x >= viewportSize.x ? (verticalRange * viewportSize.y / containerSize.y) : verticalRange);
    }

    @Override
    public Vector2f getVisibleSize() {
        return viewport.size;
    }

    @Override
    public Vector2f getWholeSize() {
        return container.size;
    }

    @Override
    public Vector2f getCurrentPosition() {
        return container.position;
    }

    @Override
    public void moveTo(Vector2f inViewportPosition) {
        container.position = inViewportPosition;
    }

    public ScrollBar getVerticalScrollBar() {
        return verticalScrollBar;
    }

    public void setVerticalScrollBar(ScrollBar verticalScrollBar) {
        this.verticalScrollBar.setViewport(null);
        this.verticalScrollBar = verticalScrollBar;
        this.verticalScrollBar.setViewport(this);
    }

    public ScrollBar getHorizontalScrollBar() {
        return horizontalScrollBar;
    }

    public void setHorizontalScrollBar(ScrollBar horizontalScrollBar) {
        this.horizontalScrollBar.setViewport(null);
        this.horizontalScrollBar = horizontalScrollBar;
        this.horizontalScrollBar.setViewport(this);
    }

    public ComponentContainer getContainer() {
        return container;
    }
}
