parser grammar MetaGramParser;

options { tokenVocab=MetaGramLexer; }

parse: (antlrGrammar | metaCode)* EOF;
antlrGrammar: (GRAMMAR | COMMENT | GRAMMAR_ASTERISK | GRAMMAR_SLASH)+;
metaCode: defaultCode otherCode;

defaultCode: META_BEGIN (metaTarget (META_SEPARATOR metaTarget)*)? META_TARGET_END META_END defaultCodeContent?;
defaultCodeContent: META_HEADER? GRAMMAR;

otherCode: META_BEGIN targetCode (META_TARGET_BEGIN targetCode)* META_END;
targetCode: metaTarget (META_SEPARATOR metaTarget)* META_TARGET_END targetCodeContent?;
targetCodeContent: META_HEADER? (META_CODE | META_PARAMS);

metaTarget: META_TARGET;