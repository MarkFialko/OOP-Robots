package gui;

/**
 * Позиция, задающаяся координатами X и Y.
 */
public class Position<T extends Number> {
    private final T m_X;
    private final T m_Y;

    public Position(T x, T y) {
        //TODO check params
        m_X = x;
        m_Y = y;
    }

    public T getX() {
        return m_X;
    }

    public T getY() {
        return m_Y;
    }
}
