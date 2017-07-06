//package com.dm.org.controller;
//
//
//import com.dm.org.base.BaseServiceInitializer;
//import com.dm.org.model.User;
//import org.junit.Test;
//import org.junit.Before;
//import org.junit.After;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.ArrayList;
//import java.util.UUID;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
///**
// * UserController Tester.
// *
// * @author XiangDe Liu qq313700046@icloud.com
// * @since
// *
// *        <pre>
// * 七月 1, 2017
// *        </pre>
// *
// * @version 1.0
// */
//
//public class UserControllerTest extends BaseServiceInitializer
//{
//    private MockMvc mockMvc;
//
//    @Mock
//    private UserDao userDao;
//
//    @Mock
//    private UserService userService;
//
//
//
//    @InjectMocks
//    private UserController userController;
//
//    @Before
//    public void before()
//        throws Exception
//    {
//        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(userController).
//                build();
//    }
//
//    @After
//    public void after()
//        throws Exception
//    {}
//
//    /**
//     * Method: setUserService(UserService userService)
//     */
//    @Test
//    public void testSetUserService()
//        throws Exception
//    {
//        // TODO: Test goes here...
//    }
//
//    /**
//     * Method: saveUser()
//     */
//    @Test
//    public void testSaveUser()
//        throws Exception
//    {
//        // TODO: Test goes here...
//    }
//
//    /**
//     * Method: findAllUsers()
//     */
//    @Test
//    public void testFindAllUsers()
//        throws Exception
//    {
//        // TODO: Test goes here...
//    }
//
//    /**
//     * Method: mockTest()
//     */
//    @Test
//    public void testMockTest()
//        throws Exception
//    {
//        ArrayList<User> userArrayList = new ArrayList<User>();
//                userArrayList.add(getMockUser());
//        when(userController.findAllUsers()).thenReturn(userArrayList);
//        mockMvc.perform(get("/demo/test"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("test"))
////                .andExpect(model().attributeExists("userList"))
//                .andDo(MockMvcResultHandlers.print());
//        verify(userService).findAllUsers();
////        System.out.println(userController.findAllUsers());
//    }
//
//    /**
//     * Method: showRegistrationForm()
//     */
//    @Test
//    public void testShowRegistrationForm()
//                throws Exception
//    {
//        User unSavedUser = getMockUser();
//        User savedUser = getMockUser();
//        when(userService.save(unSavedUser)).thenReturn(savedUser);
//        mockMvc.perform(post("/user/register")
//                .param("userId", unSavedUser.getUserId())
//                .param("age", "100")
//                .param("gender", "男")
//                .param("name", UUID.randomUUID().toString().substring(0, 8))
//        )
//                .andExpect(status().isFound())
//                .andExpect(redirectedUrl("/user/" + unSavedUser.getUserId()))
//               .andExpect(flash().attributeExists("user"))
//                .andDo(MockMvcResultHandlers.print());
//        verify(userService, atLeastOnce()).save(unSavedUser);
//    }
//
//    private User getMockUser()
//    {
//        User user = new User(UUID.randomUUID().toString().substring(0, 8),
//                UUID.randomUUID().toString().substring(0, 8),
//                null, 100);
//        return user;
//    }
//
//    /**
//     * Method: processRegistration()
//     */
//    @Test
//    public void testProcessRegistration()
//                throws Exception
//    {
//        User findUser = getMockUser();
//        User foundUser = getMockUser();
//        when(userService.getUserById(findUser.getUserId())).thenReturn(foundUser);
//        mockMvc.perform(get("/user/" + findUser.getUserId()))
//                .andExpect(status().isOk())
//                .andExpect(view().name("profile"))
//                .andExpect(model().attributeExists("userId"))
//                .andDo(MockMvcResultHandlers.print());
//        verify(userService, atLeastOnce()).getUserById(findUser.getUserId());
//    }
//
//    /**
//     * Method: showUserProfile()
//     */
//    @Test
//    public void testShowUserProfile()
//                throws Exception
//    {
//
//    }
//
//
//}
