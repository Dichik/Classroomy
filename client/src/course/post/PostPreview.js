import { React } from 'react';
import { Link } from 'react-router-dom';
import './index.css';

export default function PostPreview({ courseId, post }) {
    return (
        <div className="post-box">
            <p className="post-title">{post.title}!</p>
            {/* <input className='post-checkbox' type="checkbox" id={post.id} 
                name="done" value={post.done} defaultValue={post.done}
                onClick={() => markPostAsChecked(post)} /> */}
            <button className="post-button">
                <Link
                    id={post.id}
                    className="link-button"
                    to={{
                        pathname: window.location.pathname + `/posts/${post.id}`,
                        courseId: courseId
                    }}
                >
                    open
                </Link>
            </button>
        </div>
    );
}
