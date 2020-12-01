import React, { useState, useEffect } from 'react'
import { TextField } from '@material-ui/core'

import './InputField.scss'

export default function InputField({
  label,
  variant = 'filled',
  name,
  value,
  error,
  onChange,
  type = 'text',
}) {
  return (
    <TextField
      id={`input-field-${name}-id`}
      className="input-field"
      label={label}
      variant={variant}
      name={name}
      value={value}
      error={error}
      onChange={onChange}
      type={type}
    />
  )
}
