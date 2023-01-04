package site.metacoding.web;

import static org.assertj.core.api.Assertions.assertThat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import site.metacoding.domain.Book;
import site.metacoding.domain.BookRepository;
import site.metacoding.service.BookService;
import site.metacoding.web.dto.request.BookSaveReqDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookApiControllerTest {

	@Autowired
	private TestRestTemplate rt;

	private static ObjectMapper om;
	private static HttpHeaders headers;
	@Autowired //DI
	private BookRepository bookRepository;
	@BeforeAll
	public static void init(){
		om = new ObjectMapper();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		//나는 JSON데이터 전송한다고 명시
	}
	//더미데이터 만들기
	@BeforeEach //각 테스트 시작전에 한번씩 실행
	public void 데이터준비() {
		//given(데이터 준비)
		String title = "junit";
		String author = "겟인데어";

		Book book = Book.builder()
				.title(title)
				.author(author)
				.build();

		bookRepository.save(book);
	}

	@Sql("classpath:db/tableInit.sql")
	@Test
	public void getBookList_test() {
		//given
		//매개변수로 받고있는 것이 없으므로 생략

		//when
		HttpEntity<String> request = new HttpEntity<>(null,headers);
		ResponseEntity<String> response
				= rt.exchange("/api/v1/book"
					, HttpMethod.GET
				,request
				,String.class);

		//System.out.println(response.getBody());
		//{"code":1,"msg":"책 목록보기","body":{"items":[{"id":1,"title":"junit","author":"겟인데어"}]}}

		//then
		DocumentContext dc = JsonPath.parse(response.getBody());
		int code = dc.read("$.code");
		String title = dc.read("$.body.items[0].title");

		//(실제값,기대값)
		assertThat(code).isEqualTo(1);
		assertThat(title).isEqualTo("junit");
	}

	@Test
	public void saveBook_test() throws Exception {
		//given
		BookSaveReqDto bookSaveReqDto = new BookSaveReqDto();
		bookSaveReqDto.setTitle("스프링1강");
		bookSaveReqDto.setAuthor("겟인데어");
		/*given Data를 JSON으로 변환해야하는디.. ObjectMapper를 쓰자*/
		String body = om.writeValueAsString(bookSaveReqDto);//JSON으로 변경됨
		//body : {"title":"스프링1강","author":"겟인데어"}

		//when
		HttpEntity<String> request = new HttpEntity<>(body,headers);
		ResponseEntity<String> response
				= rt.exchange("/api/v1/book"
				, HttpMethod.POST
				,request
				,String.class);
		//3번째 : HttpEntity<?> requestEntity : body(JSON변환한거)
		//4번째 : responseType : String.class

		//System.out.println(response.getBody());
		//response.getBody() : {"code":1,"msg":"글 저장 성공","body":{"id":1,"title":"스프링1강","author":"겟인데어"}}

		//then
		//검증하는 코드가 필요하다
		DocumentContext dc = JsonPath.parse(response.getBody());
		String title = dc.read("$.body.title");
		String author = dc.read("$.body.author");

		//(실제값,기대값)
		assertThat(title).isEqualTo("스프링1강");
		assertThat(author).isEqualTo("겟인데어");
	}
}
