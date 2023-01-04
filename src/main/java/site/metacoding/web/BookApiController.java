package site.metacoding.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import site.metacoding.service.BookService;
import site.metacoding.web.dto.response.BookListRespDto;
import site.metacoding.web.dto.response.BookRespDto;
import site.metacoding.web.dto.request.BookSaveReqDto;
import site.metacoding.web.dto.response.CMRespDto;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BookApiController {

    private final BookService bookService;

    // 1. 책등록
    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(@RequestBody @Valid BookSaveReqDto bookSaveReqDto
        ,BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            Map<String,String> errorMap = new HashMap<>();
            for(FieldError fe : bindingResult.getFieldErrors()){
                errorMap.put(fe.getField(),fe.getDefaultMessage());
            }
            System.out.println("===========================");
            System.out.println(errorMap.toString());
            System.out.println("===========================");

            throw new RuntimeException(errorMap.toString());
//            return new ResponseEntity<>(CMRespDto.builder()
//                    .code(-1).msg(errorMap.toString()).body(bookRespDto).build()
//                    ,HttpStatus.BAD_REQUEST);//400 = 요청이 잘못됨
        }
        //Exception이 안일어날때만, 책 등록하기 메서드 수행되게
       BookRespDto bookRespDto = bookService.책등록하기(bookSaveReqDto);
        return new ResponseEntity<>(CMRespDto.builder()
                .code(1).msg("글 저장 성공").body(bookRespDto).build()
                ,HttpStatus.CREATED);//201,INSERT성공
    }
    // 2. 책목록보기
    @GetMapping("/api/v1/book")
    public ResponseEntity<?> getBookList(){
        BookListRespDto bookListRespDto = bookService.책목록보기();
        return new ResponseEntity<>
                (CMRespDto.builder()
                        .code(1).msg("책 목록보기").body(bookListRespDto).build()
                        ,HttpStatus.OK);//200상태코드 return
    }
    // 3.책 한 건 보기
    @GetMapping("/api/v1/book/{id}")
    public ResponseEntity<?> getBookOne(@PathVariable Long id){
        BookRespDto bookRespDto = bookService.책한건보기(id);
        return new ResponseEntity<>
                (CMRespDto.builder()
                        .code(1).msg("글 한건보기 성공").body(bookRespDto).build()
                        ,HttpStatus.OK);//200상태코드 return
    }

    // 4. 책 삭제하기
    @DeleteMapping("/api/v1/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id){
        return new ResponseEntity<>
                (CMRespDto.builder()
                        .code(1).msg("글 삭제하기 성공").body(null).build()
                        ,HttpStatus.OK);//200상태코드 return
    }

    // 5. 책 수정하기
    @PutMapping("/api/v1/book/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id
            ,@RequestBody @Valid BookSaveReqDto bookSaveReqDto
            ,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            Map<String,String> errorMap = new HashMap<>();
            for(FieldError fe : bindingResult.getFieldErrors()){
                errorMap.put(fe.getField(),fe.getDefaultMessage());
            }
            System.out.println("===========================");
            System.out.println(errorMap.toString());
            System.out.println("===========================");
            throw new RuntimeException(errorMap.toString());
        }
        BookRespDto bookRespDto = bookService.책수정하기(id,bookSaveReqDto);
        return new ResponseEntity<>
                (CMRespDto.builder()
                        .code(1).msg("글 수정 하기 성공").body(bookRespDto).build()
                        ,HttpStatus.OK);//200상태코드 return
    }
}
