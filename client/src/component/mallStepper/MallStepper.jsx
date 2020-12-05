import React, { useEffect } from 'react'
import { makeStyles } from '@material-ui/core/styles'
import Stepper from '@material-ui/core/Stepper'
import Step from '@material-ui/core/Step'
import StepLabel from '@material-ui/core/StepLabel'

const useStyles = makeStyles((theme) => ({
  root: {
    width: '100%',
  },
  // backButton: {
  //   marginRight: theme.spacing(1),
  // },
  // instructions: {
  //   marginTop: theme.spacing(1),
  //   marginBottom: theme.spacing(1),
  // },
}))

function getSteps() {
  return ['OTP Authentication', 'Username/Password', 'Address']
}

export default function MallStepper({ pathname }) {
  const classes = useStyles()
  const [activeStep, setActiveStep] = React.useState(0)
  const steps = getSteps()

  useEffect(() => {
    switch (pathname) {
      case 'email':
        setActiveStep(0)
        break
      case 'profile':
        setActiveStep(1)
        break
      case 'address':
        setActiveStep(2)
        break

      default:
        break
    }
  }, [pathname])

  return (
    <div className={classes.root}>
      <Stepper activeStep={activeStep} alternativeLabel>
        {steps.map((label) => (
          <Step key={label}>
            <StepLabel>{label}</StepLabel>
          </Step>
        ))}
      </Stepper>
    </div>
  )
}
