grammar Select;

select : SELECT columns FROM collection where? (skip? limit? | limit? skip?) ;
columns : ALL_COLUMNS | names += columnName (',' names += columnName)* ;
columnName : NAME | FIELD_NAME ;
collection : NAME ;
where : WHERE condition (AND condition)* ;
condition :
    eqCondition |
    gtCondition |
    ltCondition |
    neCondition
    ;
eqCondition : left=columnName '=' right=(NUMBER | STRING) ;
gtCondition : left=columnName '>' right=(NUMBER | STRING) ;
ltCondition : left=columnName '<' right=(NUMBER | STRING) ;
neCondition : left=columnName '<>' right=(NUMBER | STRING) ;
limit : LIMIT NUMBER ;
skip : (OFFSET|SKIP_TEXT) NUMBER ;



WHERE : W H E R E ;
AND : A N D ;
SELECT : S E L E C T ;
LIMIT : L I M I T ;
SKIP_TEXT : S K I P ;
OFFSET : O F F S E T ;
FROM : F R O M ;

WS : [ \r\n\t] -> skip ;
ALL_COLUMNS : '*' ;
NAME : [a-zA-Z][a-zA-Z0-9]* ;
FIELD_NAME : [a-z_][a-zA-Z_@#0-9]* ;
NUMBER : [0-9]+ ;
CHAR_SECUENCE : .+? ;
STRING : [']CHAR_SECUENCE['] ;


fragment A : [aA];
fragment B : [bB];
fragment C : [cC];
fragment D : [dD];
fragment E : [eE];
fragment F : [fF];
fragment G : [gG];
fragment H : [hH];
fragment I : [iI];
fragment J : [jJ];
fragment K : [kK];
fragment L : [lL];
fragment M : [mM];
fragment N : [nN];
fragment O : [oO];
fragment P : [pP];
fragment Q : [qQ];
fragment R : [rR];
fragment S : [sS];
fragment T : [tT];
fragment U : [uU];
fragment V : [vV];
fragment W : [wW];
fragment X : [xX];
fragment Y : [yY];
fragment Z : [zZ];