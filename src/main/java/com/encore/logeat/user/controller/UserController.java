package com.encore.logeat.user.controller;

import com.encore.logeat.common.dto.ResponseDto;
import com.encore.logeat.user.domain.User;
import com.encore.logeat.user.dto.request.UserCreateRequestDto;
import com.encore.logeat.user.dto.request.UserInfoUpdateRequestDto;
import com.encore.logeat.user.dto.request.UserLoginRequestDto;
import com.encore.logeat.user.dto.response.UserInfoResponseDto;
import com.encore.logeat.user.service.UserService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/user/new")
	public ResponseEntity<ResponseDto> createUser(UserCreateRequestDto userCreateRequestDto) {
		User user = userService.createUser(userCreateRequestDto);
		return new ResponseEntity<>(
			new ResponseDto(HttpStatus.CREATED, "new User Created!", user.getId()),
			HttpStatus.CREATED);
	}

	@PostMapping("/doLogin")
	public ResponseEntity<ResponseDto> userLogin(
		@RequestBody UserLoginRequestDto userLoginRequestDto) {
		ResponseDto responseDto = userService.userLogin(userLoginRequestDto);
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

	@GetMapping("/user/myfollower")
	public ResponseEntity<ResponseDto> myFollower() {
		ResponseDto responseDto = userService.getMyFollower();
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

	@GetMapping("/user/myfollowing")
	public ResponseEntity<ResponseDto> myFollowing() {
		ResponseDto responseDto = userService.getMyFollowing();
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

	@GetMapping("/checkNickname")
	public ResponseEntity<Object> userNicknameDuplicateCheck(@RequestParam String nickname) {
		boolean isDuplicate = userService.nicknameDuplicateCheck(nickname);

		if (isDuplicate) {
			return ResponseEntity.ok().body(new HashMap<String, Object>() {{
				put("result", false);
				put("message", "이미 사용 중인 닉네임입니다.");
			}});
		} else {
			return ResponseEntity.ok().body(new HashMap<String, Object>() {{
				put("result", true);
			}});
		}
	}
	@GetMapping("/checkEmail")
	public ResponseEntity<Object> userEmailDuplicateCheck(@RequestParam String email) {
		boolean isDuplicate = userService.emailDuplicateCheck(email);

		if (isDuplicate) {
			return ResponseEntity.ok().body(new HashMap<String, Object>() {{
				put("result", false);
				put("message", "이미 사용 중인 이메일입니다.");
			}});
		} else {
			return ResponseEntity.ok().body(new HashMap<String, Object>() {{
				put("result", true);
			}});
		}
	}
	@GetMapping("/user/mypage")
	public ResponseEntity<ResponseDto> myPage() {
		UserInfoResponseDto userInfo = userService.getMypage();
		return new ResponseEntity<>(new ResponseDto(HttpStatus.OK, "User info loaded successfully", userInfo), HttpStatus.OK);
	}

	@PatchMapping("/user/update")
	public ResponseEntity<ResponseDto> updateUserInfo(@RequestBody UserInfoUpdateRequestDto userInfoupdateDto) {
		userService.updateInfoUser(userInfoupdateDto);
		return new ResponseEntity<>(new ResponseDto(HttpStatus.OK, "User updated successfully", userInfoupdateDto.getNickname()), HttpStatus.OK);
	}
	@PostMapping("/user/image")
	public ResponseEntity<?> userImageUpload(@RequestParam("upload") MultipartFile request) {
		try {
			String originName = request.getOriginalFilename();

			String newFileName = java.util.UUID.randomUUID().toString() + "@" + originName;

			String imageUrl = userService.saveFile(request, newFileName);
			userService.updateUserImage(imageUrl);

			return ResponseEntity.ok(Map.of(
				"uploaded", true,
				"fileName", newFileName,
				"originName", originName,
				"url", imageUrl));

		} catch (Exception e) {
			return ResponseEntity.status(500).body(Map.of(
				"uploaded", false,
				"error", Map.of("message", "파일을 업로드하지 못했습니다")));
		}
	}
}
