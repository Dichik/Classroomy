import { React, useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import postService from '../../services/post.service';
import Spinner from '../spinner/spinner.component';
// import './index.css';

export default function Post() {
    const { id } = useParams();
    const courseId = window.location.pathname.split('/')[2];
    const navigate = useNavigate();

    const [loading, setLoading] = useState(false);
    const [post, setPost] = useState({ title: '', description: '' });
    const [answer, setAnswer] = useState('');
    const [err, setErr] = useState('');

    const handleClick = () => {
        navigate(-1);
    };

    const getPostById = async (id) => {
        setLoading(true);
        postService
            .getPostById(courseId, id)
            .then((response) => response.data)
            .then((data) => {
                setPost(data);
                setAnswer(data.answer);
            })
            .catch((err) => setErr(err.message))
            .finally(() => setLoading(false));
    };

    const handleSubmit = async () => {
        const body = {
            answer: answer
        };
        const requestOptions = {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json'
            },
            body: JSON.stringify(body)
        };
        try {
            const response = await fetch(
                `http://localhost:8080/posts/${id}`,
                requestOptions
            );
            if (!response.ok) {
                throw new Error(`Error! status: ${response.status}`);
            }

            const data = await response.json();
            setPost(data);
            setAnswer(data.answer);
            console.log('post is ', data);
        } catch (err) {
            setErr(err.message);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        setLoading(true);
        getPostById(id);
        setLoading(false);
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
                            <p>
                                deadline:{' '}
                                {post.deadline
                                    ? post.deadline.substring(0, 10)
                                    : 'No deadline'}
                            </p>
                        </div>
                        <button className="sht-button" onClick={handleClick}>
                            back to course
                        </button>
                    </div>

                    <h3>
                        <b>what to do:</b>
                    </h3>
                    <div className="sub-header-description">
                        <h2>{post.description}</h2>
                    </div>

                    <h3>
                        <b>your answer:</b>
                    </h3>
                    <form id="create-course" className="form-style">
                        <input
                            id="answer"
                            className="student-answer-field"
                            type="text"
                            placeholder="enter your response"
                            value={answer}
                            onChange={(e) => setAnswer(e.target.value)}
                        />
                        <button onClick={handleSubmit}>submit</button>
                    </form>
                </div>
            )}
        </div>
    );
}
