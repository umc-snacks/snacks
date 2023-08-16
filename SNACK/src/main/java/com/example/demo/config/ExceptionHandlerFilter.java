package com.example.demo.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.NoSuchElementException;

@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    /**
     * 토큰 관련 에러 핸들링
     * JwtTokenFilter 에서 발생하는 에러를 핸들링해준다.
     * <토큰의 유효성 검사>
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            // 다음 filter Chain에 대한 실행 (filter-chain의 마지막에는 Dispatcher Servlet이 실행된다.)
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {

            //토큰의 유효기간 만료
            log.error("만료된 토큰입니다_ExceptionHandlerFilter안입니다!");

            setErrorResponse(response, ErrorCode.EXPIRED_TOKEN);

        } catch (JwtException | IllegalArgumentException e) {

            //유효하지 않은 토큰
            log.error("유효하지 않은 토큰이 입력되었습니다._ExceptionHandlerFilter안입니다!");
            setErrorResponse(response, ErrorCode.INVALID_TOKEN);

        } catch (NoSuchElementException e) {

            //사용자 찾을 수 없음
            log.error("사용자를 찾을 수 없습니다._ExceptionHandlerFilter안입니다!");
            setErrorResponse(response, ErrorCode.INVALID_PERMISSION);

        } catch (ArrayIndexOutOfBoundsException e) {

            log.error("토큰을 추출할 수 없습니다._ExceptionHandlerFilter안입니다!");
            setErrorResponse(response, ErrorCode.INVALID_TOKEN);

        } catch (NullPointerException e) {

            filterChain.doFilter(request, response);
        }
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode exceptionCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject responseJson = new JSONObject();
        responseJson.put("message", exceptionCode.getMessage());
        responseJson.put("code", exceptionCode.getHttpStatus());

        response.getWriter().print(responseJson);
    }


}
