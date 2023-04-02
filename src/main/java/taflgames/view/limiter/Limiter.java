package taflgames.view.limiter;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The code was taken from:
 * https://stackoverflow.com/questions/3519151/how-to-limit-the-number-of-characters-in-jtextfield
 * This class is used to limit the number of characters that can be inserted
 * in a particular field of the GUI.
 */
public class Limiter extends PlainDocument {
    private static final Logger LOGGER = LoggerFactory.getLogger(Limiter.class);
    private final int limit;

    /**
     * Builds a new Limiter.
     * @param limit the maximum number of characters that can be written.
     */
    public Limiter(final int limit) {
        super();
        this.limit = limit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertString(final int offset, final String str, final AttributeSet attr) {
        if (str == null) {
            return;
        }

        if (this.getLength() + str.length() <= limit) {
            try {
                super.insertString(offset, str, attr);
            } catch (BadLocationException e) {
                LOGGER.error("Exception occurred!", e);
            }
        }
    }
}
