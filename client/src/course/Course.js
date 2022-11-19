import './index.css'

export default function Course({ course }) {

    return (
        <div key={course.id} className="course-box flex-row-item">
            <h2 className='course-title'>{course.name}</h2>
        </div>
    )

}