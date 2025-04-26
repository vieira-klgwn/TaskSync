import React, {useEffect, useState, createContext} from "react";
import axios from 'axios';
import {useNavigate} from 'react-router-dom';

export const  AuthContext = createContext();


export  const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [accessToken, setAccessToken] = useState(localStorage.getItem('accessToken') || null);
    const [refreshToken, setRefreshToken] = useState(localStorage.getItem('refreshToken') || null);
    const navigate = useNavigate();

    useEffect(() => {
        if (accessToken) {
            axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
            axios.get('/api/users/me')
                .then(response => {
                    setUser(response.data)
                })
                .catch(
                    () => logout()
                )
        }
    },[accessToken]);

    const login = async (email, password) => {
        try{
            const response = await axios.post('/api/auth/login', {email,password});
            const {accessToken,refreshToken} = response.data;
            setAccessToken(accessToken);
            setRefreshToken(refreshToken);
            localStorage.setItem('accessToken', accessToken);
            localStorage.setItem('refreshToken',refreshToken);// test this by rewriting it again and see the popups
            axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
            const userResponse = await  axios.get('/api/users/me');
            setUser(userResponse.data)
            navigate('/dashboard');
        }catch (error){
            throw new Error(error.response?.data?.message || 'Login failed')
        }
    }

    const register = async (firstName, lastName, email , password, role) => {
       try {
        const response =await axios.post('/api/auth/register',{
            firstName,
            lastName,
            email,
            password,
            role
        });
        const {accessToken,refreshToken} = response.data;

        setAccessToken(accessToken);
        setRefreshToken(refreshToken);
        localStorage.setItem('accessToken', accessToken);
        localStorage.setItem('refreshToken', refreshToken); // Test this too like in login
        axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
        const userResponse = await axios.get('/api/users/me');
        setUser(userResponse.data);
        navigate('/dashboard');


       }catch (error) {
            throw new Error(error.response?.data?.message || 'Registration failed');
    }

    const refreshAccessToken = async () => {
           try {
               const response = await axios.post('/api/auth/refresh-token',{},{headers: {Authorization: `Bearer ${refreshToken}`}})
               const {accessToken,refreshToken:newRefreshToken}= response.data;
               setAccessToken(accessToken);
               setRefreshToken(newRefreshToken);
               localStorage.setItem('accessToken',accessToken);
               localStorage.setItem('refreshToken',newRefreshToken)
               axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
               return accessToken;
           }catch (error) {
               logout();
               throw new Error('Session expired, please log in again')
           }
           }
    }

    const logout = () => {
        setUser(null)
        setAccessToken(null)
        setRefreshToken(null)
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        delete axios.defaults.headers.common['Authorization'];
        navigate('/login');
    };

    // Axios interceptor to handle 401 errors
    useEffect(() => {
        const interceptor = axios.interceptors.response.use(
            response => response,
            async error => {
                if (error.response?.status === 401 && refreshToken) {
                    try {
                        await refreshAccessToken;
                        // Retry the original request
                        return axios(error.config);
                    } catch (refreshError) {
                        logout();
                        return Promise.reject(refreshError);
                    }
                }
                return Promise.reject(error);
            }
        );
        return () => axios.interceptors.response.eject(interceptor);
    }, [refreshToken]);

    return (
        <AuthContext.Provider value={{user,accessToken,refreshToken,login,register,logout,refreshAccessToken}}>
            {children}
        </AuthContext.Provider>
    );
};
