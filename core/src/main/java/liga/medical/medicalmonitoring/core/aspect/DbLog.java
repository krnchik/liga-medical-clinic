package liga.medical.medicalmonitoring.core.aspect;

import liga.medical.common.dto.LogDto;
import liga.medical.common.dto.Status;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class DbLog {

    private final RestTemplate template;
    private final String baseUri = "http://localhost:8082";

    @Autowired
    public DbLog(RestTemplate restTemplate) {
        this.template = restTemplate;
    }

    @Pointcut("@annotation(liga.medical.medicalmonitoring.core.annotation.DbLog)")
    public void annotationProcessingMethods() {

    }

    @AfterReturning(pointcut = "annotationProcessingMethods()")
    public void logMethodDebug(JoinPoint jp) {
        log.error(sendLog(jp, "/exception"));
    }

    private String sendLog(JoinPoint jp, String path) {
        String methodName = jp.getSignature().getName();
        String className = jp.getSignature().getDeclaringTypeName();
        String params = Arrays.toString(jp.getArgs());

        String log = "methodName: " + methodName + " className: " + className +
                " params: " + params;

        LogDto logDto = new LogDto();
        logDto.setSystemTypeId(Status.PERSON_SERVICE);
        logDto.setMethodParams(log);

        URI uri = null;
        try {
            uri = new URI(baseUri + path);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        template.postForObject(uri, logDto, String.class);
        return log;
    }
}
