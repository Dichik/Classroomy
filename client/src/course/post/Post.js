import {React, useEffect, useState} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import './index.css';


export default function Post() {
    const {id} = useParams();
    const navigate = useNavigate();

    const [loading, setLoading] = useState(false);
    const [post, setPost] = useState({'title': '', 'description': ''});
    const [err, setErr] = useState('');

    const handleClick = () => {
        navigate(-1);
    }

    const getPostById = async (id) => {
        try {
            const response = await fetch(`http://localhost:8080/posts/${id}`);
            if (!response.ok) {
                throw new Error(`Error! status: ${response.status}`);
            }

            const data = await response.json();
            setPost(data);
            console.log('post is ', data);
        } catch (err) {
            setErr(err.message);
        } finally {
            setLoading(false);
        }
    }

    const handleSubmit = () => {
        console.log('assignment was submitted!');
    }

    useEffect(() => {
        setLoading(true);
        getPostById(id);
    }, [])

    if (loading) {
        return <h1>Loading...</h1>
    }

    return (
        <div>
            {err && <h2>{err}</h2>}
            {!err && <div>
                <h1>{post.title}</h1>
                <div>
                    <h2>{post.description}</h2>
                </div>
                <input className='student-answer-field' type='text'></input>
                <button className='submit-button' onClick={handleSubmit}>submit</button>
                <button onClick={handleClick}>back to course</button>
            </div>}
        </div>
    );

}