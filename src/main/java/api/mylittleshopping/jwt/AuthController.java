package api.mylittleshopping.jwt;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        if ("admin".equals(username) && "1234".equals(password)) {
            String token = jwtTokenUtil.generateToken(username);
            return ResponseEntity.ok(Map.of("access_token", token));
        }

        return ResponseEntity.status(401).body("Credenciais inválidas");
    }

    @GetMapping("/ping")
    public String ping() {
        return "Sem autenticação";
    }

}
