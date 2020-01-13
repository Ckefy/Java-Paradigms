tree_build([],[]).
tree_build(List, Tree):- makeLeaves(List, Nodes), makeTree(Nodes, Tree).

makeLeaves([],[]).
makeLeaves([(Key, Value)| T], ResultList):- makeLeaves(T, Result), ResultList = [node(Key, Value, [],[],[],-1,-1,Key)|Result].
%обойти весь массив и сделать листья

makeTree([H|T], Tree):- T==[], Tree = H.
makeTree([H|T], Tree):- T\=[], getRow([H|T], NextRow), makeTree(NextRow, Tree).
%берем по 3, потом переходим к тому что получилось и повторяем

getRow([],[]).
getRow([H|T],[H]):- T == [].

getRow([Child1|[Child2|T]],[node(-1, -1, Child1, Child2, [], Key1, Key2, Key2)]):-
   T == [],
  Child1 = node(_, _, _, _, _, _, _, Key1),
  Child2 = node(_, _, _, _, _, _, _, Key2). %максимальное значение присваиваем, то есть справа

getRow([Child1|[Child2|[Child3|T]]],[node(-1, -1, Child1, Child2, Child3, Key1, Key2, Key3)|ResultList]):-
 Child3 \= [],
 Child1 = node(_, _, _, _, _, _, _, Key1),
 Child2 = node(_, _, _, _, _, _, _, Key2),
 Child3 = node(_, _, _, _, _, _, _, Key3),
 getRow(T, ResultList).
%первые 2 - ключ и значение вершины нынешней, потом 3 ссылки на детей, последние 3 это ключи детей;
%если лист, то ключ в последнем поле

map_get(node(Key, Value, [], [], [], -1, -1, Key), Key, Value).
map_get(node(_, _, Child1, Child2, Child3, Key1, Key2, Key3), Key, Value) :-
(Key =< Key1->map_get(Child1, Key, Value);
 Key =< Key2->map_get(Child2, Key, Value);
 map_get(Child3, Key, Value)).

map_size([], 0).
map_size(node(Key, Value, [], [], [], -1, -1, Key), 1).

map_size(node(-1, -1, Child1, Child2, Child3, _, _, _), Size) :-
 map_size(Child1, Size1), map_size(Child2, Size2), map_size(Child3,Size3),
 Size is Size1 + Size2 +Size3.