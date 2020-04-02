grammar Select;


select : 'SELECT' columns 'FROM' collection where? skip? limit? ;
columns : ALL_COLUMNS | names += columnName (',' names += columnName)* ;
columnName : IDENTIFIER ;
collection : IDENTIFIER ;
where : 'WHERE' condition ('AND' condition)? ;
condition :
    eqCondition |
    gtCondition |
    ltCondition |
    neCondition
    ;
eqCondition : left=IDENTIFIER '=' right=(NUMBER | STRING) ;
gtCondition : left=IDENTIFIER '>' right=(NUMBER | STRING) ;
ltCondition : left=IDENTIFIER '<' right=(NUMBER | STRING) ;
neCondition : left=IDENTIFIER '<>' right=(NUMBER | STRING) ;
limit : 'LIMIT' NUMBER ;
skip : ('OFFSET'|'SKIP') NUMBER ;



WS : [ \r\n\t] -> skip ;
ALL_COLUMNS : '*' ;
IDENTIFIER : [a-zA-Z]+ ;
NUMBER : [0-9]+ ;
CHAR_SECUENCE : .+? ;
STRING : [']CHAR_SECUENCE['] ;