package Vishwaas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class VerificationTest {

    @Mock
    private Verify verify;

    @Mock
    private CertificateData certificatedata;

    @Mock
    private Token tokentest;

    @InjectMocks
    private Verification verifyAPI;
  

    @BeforeEach
    public void setUp() {
        
    }

    @Test
    public void testVerifyAPi_Success() throws JsonProcessingException {
        
        String osid = "12345";
        String expectedResponse = "Success";
        Mockito.when(verify.verifyCertificate(eq(osid))).thenReturn(ResponseEntity.ok(expectedResponse));

 
        ResponseEntity<String> response = verifyAPI.verifyAPi(osid);

       
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testVerifyAPi_IdNotPassed() throws JsonProcessingException {
    
        String osid = null;
        Mockito.when(verify.verifyCertificate(eq(osid))).thenReturn(ResponseEntity.notFound().build());

      
        ResponseEntity<String> response = verifyAPI.verifyAPi(osid);

                assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testVerifyAPi_InvalidToken() throws JsonProcessingException {
       
        String osid = "12345";
        Mockito.when(verify.verifyCertificate(eq(osid))).thenReturn(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());

      
        ResponseEntity<String> response = verifyAPI.verifyAPi(osid);

    
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

}

