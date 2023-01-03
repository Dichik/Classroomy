import { React, useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import Spinner from '../../spinner/Spinner';
import './index.css';

export default function Post() {
    const { id } = useParams();
    const navigate = useNavigate();

    const [loading, setLoading] = useState(false);
    const [post, setPost] = useState({ title: '', description: '' });
    const [answer, setAnswer] = useState('');
    const [err, setErr] = useState('');

    const handleClick = () => {
        navigate(-1);
    };

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
    };

    const handleSubmit = () => {
        console.log('assignment was submitted!', answer);
    };

    useEffect(() => {
        setLoading(true);
        getPostById(id);
    }, []);

    if (loading) {
        return <Spinner />;
    }

    return (
        <div>
            {err && <h2>{err}</h2>}
            {!err && (
                <div>
                    <div className="sub-header-title">
                        <div className="sht-title">
                            <h1>{post.title}</h1>
                        </div>
                        <button className="sht-button" onClick={handleClick}>
                            back to course
                        </button>
                    </div>
                    <div className="sub-header-description">
                        <h2>{post.description}</h2>
                    </div>
                    <form id="create-course" className="form-style">
                        <input
                            id="answer"
                            className="student-answer-field"
                            type="text"
                            placeholder="enter your response"
                            onChange={(e) => setAnswer(e.target.value)}
                        />
                        <button onClick={handleSubmit}>submit</button>
                    </form>
                </div>
            )}
        </div>
    );
}
