package com.mysite.sbb;

import org.commonmark.node.Node;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;
import org.commonmark.parser.Parser;

@Component
public class CommonUtil {
    public String markdown(String markdown) { //마크다운 컴포넌트
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }
}
