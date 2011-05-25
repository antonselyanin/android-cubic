package antonj.opengl.text;

import antonj.opengl.texture.TexDetails;

/**
 * User: Anton
 * Date: 09.02.2010
 * Time: 22:33:03
 */
public class Glyph {
    public TexDetails tex;
    public int advance;

    public Glyph(TexDetails tex, int advance) {
        this.tex = tex;
        this.advance = advance;
    }
}
