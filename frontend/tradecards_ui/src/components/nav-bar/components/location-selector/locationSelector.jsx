import React from 'react';
import PropTypes from 'prop-types';
import { cities } from './constants';

const LocationSelector = (props) => {
  const {
    onDetectLocation,
    onLocationChange,
    error,
  } = props;

  return (
    <div className='location-selector'>
      <div
        className='location-selector-detector'
        onClick={onDetectLocation}
      >
        <div className='location-selector-detector-prompt'>
          <i
            className="fas fa-map-marker-alt"
          />
          <div>Detect my location</div>
        </div>
        <div>{error}</div>
      </div>
      <div className='location-selector-suggestion'>
        <div className='location-selector-suggestion-title'>
          Popular Cities
        </div>
        <div className='location-selector-suggestion-container'>
          {
            cities.map((city) => (
              <div
                key={city}
                onClick={() => onLocationChange(city)}
                className='location-selector-suggestion-container-city'
              >
                {city}
              </div>
            ))
          }
        </div>
      </div>
    </div>
  );
};

LocationSelector.propTypes = {
  onDetectLocation: PropTypes.func.isRequired,
  onLocationChange: PropTypes.func.isRequired,
  error: PropTypes.string,
};

LocationSelector.defaultProps = { error: '', };

export default LocationSelector;
