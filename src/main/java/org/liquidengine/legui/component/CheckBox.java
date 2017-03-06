package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.MouseClickEventListener;

import java.util.HashMap;

import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.CLICK;

/**
 * An implementation of a check box -- an item that can be selected or
 * deselected, and which displays its state to the user.
 */
public class CheckBox extends Controller implements TextComponent {

    /**
     * Check box text state
     */
    protected TextState textState;

    /**
     * Checkbox state.
     */
    protected boolean                 checked;
    private   MouseClickEventListener mouseClickEventListener;

    /**
     * Default constructor which initialize checkbox with "CheckBox" text.
     */
    public CheckBox() {
        this("CheckBox");
    }

    /**
     * Constructor with position and size parameters.
     * Initialize checkbox with "CheckBox" text.
     *
     * @param x      x position position in parent component
     * @param y      y position position in parent component
     * @param width  width of component
     * @param height height of component
     */
    public CheckBox(float x, float y, float width, float height) {
        this("CheckBox", x, y, width, height);
    }

    /**
     * Constructor with position and size parameters.
     * Initialize checkbox with "CheckBox" text.
     *
     * @param position position position in parent component
     * @param size     size of component
     */
    public CheckBox(Vector2f position, Vector2f size) {
        this("CheckBox", position, size);
    }

    /**
     * Default constructor which initialize checkbox with specified text.
     *
     * @param text specified text for checkbox
     */
    public CheckBox(String text) {
        initialize(text);
    }

    /**
     * Constructor with position and size parameters.
     * Initialize checkbox with "CheckBox" text.
     *
     * @param text   specified text for checkbox
     * @param x      x position position in parent component
     * @param y      y position position in parent component
     * @param width  width of component
     * @param height height of component
     */
    public CheckBox(String text, float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize(text);
    }

    /**
     * Constructor with position and size parameters.
     * Initialize checkbox with "CheckBox" text.
     *
     * @param text     specified text for checkbox
     * @param position position position in parent component
     * @param size     size of component
     */
    public CheckBox(String text, Vector2f position, Vector2f size) {
        super(position, size);
        initialize(text);
    }

    /**
     * Used to initialize checkbox state.
     *
     * @param text
     */
    private void initialize(String text) {
        this.textState = new TextState(text);
        setBackgroundColor(ColorConstants.transparent());
        setBorder(null);

        mouseClickEventListener = new CheckBoxMouseClickEventListener();
        getListenerMap().addListener(MouseClickEvent.class, mouseClickEventListener);
    }

    /**
     * Returns current text state.
     *
     * @return text state of component.
     */
    public TextState getTextState() {
        return textState;
    }

    /**
     * Used to set text state.
     *
     * @param textState new state to set.
     */
    public void setTextState(TextState textState) {
        if (textState != null) {
            this.textState = textState;
        } else {
            this.textState = new TextState();
        }
    }

    /**
     * Returns true if checkbox is checked
     *
     * @return true if checkbox is checked
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * Used to change checked state
     *
     * @param checked new checkbox state
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CheckBox checkBox = (CheckBox) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(checked, checkBox.checked)
                .append(textState, checkBox.textState)
                .isEquals();
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(textState)
                .append(checked)
                .toHashCode();
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("textState", textState)
                .append("checked", checked)
                .toString();
    }

    /**
     * MouseClickEventListener for checkbox, used to toggle checkbox state on mouse click.
     */
    public static class CheckBoxMouseClickEventListener implements MouseClickEventListener {

        @Override
        public void process(MouseClickEvent event) {
            CheckBox checkBox = (CheckBox) event.getComponent();
            if (event.getAction() == CLICK) {
                checkBox.setChecked(!checkBox.isChecked());
            }
        }

        @Override
        public boolean equals(Object obj) {
            return true;
        }
    }
}
