package com.dm.org.client;

import com.dm.org.service.StatelessCredentialsService;
import com.dm.org.service.impl.StatelessCredentialsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Before;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in  17:22 2017/9/14.
 * @since data-minning-platform
 */

public class AbstractClientTest {
//    static RestTemplate restTemplate;
    ObjectMapper objectMapper;
    protected String baseURL = "http://localhost:8080/dm";
    protected HttpClient client = new DefaultHttpClient();
    StatelessCredentialsService service = new StatelessCredentialsServiceImpl();

    @Before
    public void setUp() throws Exception {
//        restTemplate = new RestTemplate();
        //        serviceServer = MockRestServiceServer.createServer(r)

    }
}
