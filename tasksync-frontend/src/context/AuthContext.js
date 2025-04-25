import React, {useEffect, useState, createContext} from "react";
import axios from 'axios';

export const  AuthContext = createContext();


export  const AuthProvider = ({children}) => {
    const [user, setUser] = useState(null);
    const [token, setToken] = useState(localStorage.getItem('token') || null);

    useEffect(() => {
        if (token) {
            axios.get("http://localhost:8080/api/users/me" , {
                header: {Authorization: `Bearer ${token}`}
            })
                .then(response => {
                    setUser(response.data)
                })
                .catch(
                    () => logout()
                )
        }
    },[token]);

    const login = async (email, password) => {
        try{
            const response = axios.post("http://localhost:8080/api/auth/authenticate",{email,password})
            const {accessToken} = response.data;
            setToken(accessToken);
            localStorage.setItem('token',accessToken);
            axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
            const userResponse = await  axios.get("http://localhost:8080/api/users/me",{headers: { Authorization: `Bearer ${accessToken}`}})
            setUser(userResponse.data)
        }catch (error){
            throw new Error("Login failed")
        }
    }

    const register = async (firstname, lastname, email , password, role) => {
        const response = axios.post('http://localhost:8080/api/auth/register',{
            firstname,
            lastname,
            email,
            password,
            role
        })
        const {accessToken} = response.data;

        setToken(accessToken)
        localStorage.setItem('token', accessToken);
        axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`
        const userResponse = await axios.get('http://localhost:8080/api/users/me',{headers: {Authorization: `Bearer ${accessToken}`}});
        setUser(userResponse);
    }

    const logout = () => {
        setToken((null))
        setUser(null)
        localStorage.removeItem('token')

        axios.post('http://localhost:8080/api/auth/logout', {headers: { Authorization: `Bearer ${token}`}});
    }

    return <AuthContext.Provider value={{user,token,login,register,logout}}>
        {children}
    </AuthContext.Provider>
}
