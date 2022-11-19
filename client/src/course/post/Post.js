import {React, useState} from 'react';
import './index.css';
import todoTick from '../../images/tick-grey-icon.png';
import doneTick from '../../images/tick-green-icon.png';


const Post = ({ post }) => {
    const [done, setDone] = useState(false);

    // TODO save done to prevent refresh data loose

    return (
        <div className='post-box'>
            <img src={done ? doneTick : todoTick} 
                alt='todo-tick' className='tick-todo-image' 
                onClick={() => setDone(!done)} 
            />
            <p>Hello from Post {post.title}!</p>
        </div>
    );

}

export default Post;