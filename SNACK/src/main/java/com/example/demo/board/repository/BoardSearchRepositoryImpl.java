package com.example.demo.board.repository;

import com.example.demo.Games;
import com.example.demo.board.entity.BoardSearch;
import com.example.demo.board.entity.QBoard;
import com.example.demo.board.entity.Board;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.demo.board.QBoard.board;


@Repository
public class BoardSearchRepositoryImpl {
    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public BoardSearchRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Board> searchBoard(BoardSearch boardSearch) {

        QBoard board = QBoard.board;

        return jpaQueryFactory
                .select(board)
                .from(board)
                .where(
                        eqTitle(boardSearch.getTitle()),
                        eqGameTitle(boardSearch.getGameTitle(), boardSearch.getEtcTitle())
                ).fetch();
    }

    private BooleanExpression eqTitle(String title) {
        if (StringUtils.isBlank(title)) {
            return null;
        }
        return board.title.containsIgnoreCase(title);
    }

    private BooleanExpression eqGameTitle(Games games, String etcTitle) {
        if (games == Games.ETC) {
            return board.etcTitle.eq(etcTitle);
        }
        return board.gameTitle.eq(games);
    }




}
