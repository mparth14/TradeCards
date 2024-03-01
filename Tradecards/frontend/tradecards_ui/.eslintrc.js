module.exports = {
  env: {
    browser: true,
    es2021: true,
  },
  extends: [
    'standard',
    'plugin:react/recommended',
  ],
  overrides: [
    {
      env: { node: true, },
      files: [
        '.eslintrc.{js,cjs}',
      ],
      parserOptions: { sourceType: 'script', },
    },
  ],
  parserOptions: {
    ecmaVersion: 'latest',
    sourceType: 'module',
  },
  plugins: [
    'react',
  ],
  rules: {
    semi: [1, 'always',],
    'react/jsx-filename-extension': [1, { extensions: ['.js', '.jsx',], },],
    'comma-dangle': ['error', {
      arrays: 'always',
      objects: 'always',
      imports: 'never',
      exports: 'always',
      functions: 'never',
    },],
    'object-curly-newline': ['error', { multiline: true, },],
    'object-curly-spacing': ['error', 'always',],
    'react/jsx-max-props-per-line': [1, { maximum: 1, when: 'always', },],
    'react/jsx-indent-props': ['error', 2,],
  },
};
