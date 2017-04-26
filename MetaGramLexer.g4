lexer grammar MetaGramLexer;

META_BEGIN: '/*' VOID? '<' -> pushMode(Meta);
COMMENT_BEGIN: '/*' VOID? -> more, pushMode(Comment);
GRAMMAR: ~[*/]+;

fragment WS: (' ' | '\t')+;
fragment VOID: (' ' | '\t' | '\n' | '\r')+;

mode Meta;
META_TARGET: [a-zA-Z0-9]+;
META_SEPARATOR: VOID? ',' VOID?;
META_TARGET_BEGIN: VOID? '<' VOID?;
META_TARGET_END: VOID? '>' VOID?;
META_HEADER: ('@header' | '@members') VOID?;
META_CODE: '{' (META_CODE | ~[{}])* '}';
META_PARAMS: '[' (META_PARAMS | ~('['|']'))* ']';
META_END: VOID? '*/' WS? -> popMode;

mode Comment;
COMMENT_CONTENT: ~[*/]+ -> more;
COMMENT_ASTERISK: '*' -> more;
COMMENT_SLASH: '/' -> more;
COMMENT: '*/' -> popMode;