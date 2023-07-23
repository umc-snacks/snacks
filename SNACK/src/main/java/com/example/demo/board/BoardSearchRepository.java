package com.example.demo.board;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.demo.board.QBoard.board;

@Repository
public class BoardSearchRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public BoardSearchRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Board> searchBoard(BoardSearch boardSearch) {

        QBoard board = QBoard.board;

        return jpaQueryFactory
                .select(board)
                .from(board)
                .where(
                        eqTitle(boardSearch.getTitle())
                ).fetch();
    }

    private BooleanExpression eqTitle(String title) {
        if (StringUtils.isBlank(title)) {
            return null;
        }
        return board.title.containsIgnoreCase(title);
    }

//    private BooleanExpression eqGameTitle(Games games) {
//        if (StringUtils.isBlank(games.name())) {
//            return null;
//        }
//        return board.title.containsIgnoreCase(gameTitle);
//    }


}
