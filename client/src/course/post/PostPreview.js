import { React } from 'react';
import { Link } from 'react-router-dom';
import './index.css';

export default function PostPreview({ post }) {
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
                    to={window.location.pathname + `/posts/${post.id}`}
                >
                    open
                </Link>
            </button>
        </div>
    );
}
