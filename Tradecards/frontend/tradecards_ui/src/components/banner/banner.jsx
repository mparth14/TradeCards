import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import './style.scss'; // Create a CSS file for styling if necessary

const Banner = (props) => {
  const {
    slides,
    interval,
  } = props;
  console.log(props);
  const [current, setCurrent,] = useState(0);

  const onSlideChange = (index) => {
    setCurrent(index);
  };

  useEffect(() => {
    const intervalId = setInterval(() => {
      setCurrent((current + 1) % slides.length);
    }, interval);
    return () => clearInterval(intervalId);
  }, [current, slides.length, interval,]);

  return (
    <div className="slideshow-container">
      {slides.map((slide, index) => (
        <div
          key={index}
          className={`slide ${index === current ? 'active' : ''}`}
          onClick={slide?.onClick}
        >
          <img src={slide.image}
            alt={slide.caption} />
        </div>
      ))}
      <div className="dot-progress">
        {slides.map((_, index) => (
          <span
            key={index}
            className={`dot ${index === current ? 'active-dot' : ''}`}
            onClick={() => onSlideChange(index)}
          ></span>
        ))}
      </div>
      <div className="arrow arrow-left"
        onClick={() => onSlideChange((current - 1 + slides.length) % slides.length)}>
        &#10094;
      </div>
      <div className="arrow arrow-right"
        onClick={() => onSlideChange((current + 1) % slides.length)}>
        &#10095;
      </div>
    </div>
  );
};
Banner.propTypes = {
  slides: PropTypes.arrayOf(PropTypes.shape({})).isRequired,
  interval: PropTypes.number,
};

Banner.defaultProps = { interval: 2000, };

export default Banner;
