import { React, useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import PostPreview from '../post/PostPreview';
import './index.css';

export default function Course() {

    const [loading, setLoading] = useState(false); {/* TODO add spinner */}
    const [posts, setPosts] = useState([]);
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [err, setErr] = useState('');

    const location = useLocation();
    const course = location.state.course;
    const navigate = useNavigate();

    const getAllPosts = () => {
        fetch(`http://localhost:8080/courses/${location.state.course.id}/posts`)
            .then((response) => response.json())
            .then((data) => {
                setPosts(data);
                setLoading(false);
            });
    };

    useEffect(() => {
        setLoading(true);
        getAllPosts();
        setLoading(false);
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    const handlePostSubmit = async () => {
        setLoading(true);
        const post = {
            "title": title,
            "description": description,
            "courseId": course.id
        };
        const requestOptions = {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json',
                Accept: 'application/json'
            },
            body: JSON.stringify(post)
        };
        try {
            const response = await fetch(`http://localhost:8080/posts`, requestOptions);
            if (!response.ok) {
                throw new Error(`Error! status: ${response.status}`);
            }
    
            const data = await response.json();
            posts.push(data);
            console.log('result is', data);
        } catch (err) {
            setErr(err.message);
        } finally {
            setLoading(false);
        }
    }

    const handleBackClick = () => {
        navigate(-1);
    }

    return (
        <div>
            {err && <h2>{err}</h2>} {/* TODO add popup window with the error */}
            <div className="course-details-page">
                <div className="sub-header">
                    <h2 className="course-details-title">{
                        course === null || course.name !== null ? course.name : 'loading course name...'
                    }</h2>
                    {/* <div className="course-actions">
                        <label id="actions">choose an action: </label>

                        <select name="actions" id="actions" onChange={doSomething}>
                            <option value="no-action" defaultChecked>no action</option>
                            <option value="mark-as-done">mark as done</option>
                        </select>
                    </div> */}
                    <div>
                        <button onClick={handleBackClick}>back</button>
                    </div>
                </div>
                <div>
                    <input id='title' name='title' 
                        className='title-input' type='text' 
                        onChange={(e) => setTitle(e.target.value)} />
                    <input id='description' name='description' 
                        className='description-input' type='text' 
                        onChange={(e) => setDescription(e.target.value)} />
                    <button className='submit-post-button' onClick={handlePostSubmit}>create post</button>
                </div>
                {posts.length !== 0 ? posts.map((post) => {
                    return <PostPreview key={post.id} post={post} />;
                }) : <h2 className='no-posts-title'>There are no posts for this course!</h2>}
            </div>
        </div>
    );
}