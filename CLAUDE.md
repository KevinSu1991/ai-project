# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

This is a React component library ("core-components") extracted from a larger enterprise platform (Shanghai Electric). It provides reusable UI components, layout primitives, form controls, and SCSS styles — designed for a Metronic-themed admin dashboard. The components are **read-only, published artifacts**; do not modify them directly. For changes, contact xiaozhw@shanghai-electric.com.

## Tech Stack

- **React** (with TypeScript) — functional components + hooks
- **React Bootstrap** for base UI primitives (Button, etc.)
- **Formik** for form state management
- **Redux** (react-redux) for app-level state (auth permissions)
- **SCSS** (Metronic v7 theme) — Bootstrap 4.x based, compiled via `style.react.scss`
- **Lodash** (`lodash-es`), **clsx**, **object-path** as utility dependencies
- **react-syntax-highlighter** for JSON/code display
- **react-inlinesvg** for inline SVG rendering
- **BroadcastChannel API** for cross-tab communication
- **Qiankun** micro-frontend support (detected via `window.__POWERED_BY_QIANKUN__`)

## Directory Structure

```
core-components/
├── components/          # All exported React components
│   ├── index.tsx        # Barrel export — public API surface
│   ├── accordion/       # Multi-file accordion (context-based)
│   ├── calendar/        # Calendar component
│   ├── cron/            # Cron expression editor
│   ├── forms/           # Form controls (Input, Select, Switch, DatePicker, Cascader, etc.)
│   ├── helper/          # Shared utilities (useAuth, cookies, screen detection, etc.)
│   ├── hooks/           # Shared hooks (inside components/)
│   ├── i18n/            # Internationalization
│   ├── layout/          # Layout system (Layout, Content, aside, header, footer, subheader)
│   ├── pagination/      # Pagination components
│   ├── supper-table/    # Advanced data table (SuperTable)
│   ├── svgs/            # Inline SVG icon components
│   ├── table/           # Table helpers (pagination, row selection, sorting)
│   ├── tour/            # Product tour component
│   └── tree/, tree-select/, super-select/, super-rate/ ...
├── hooks/               # Top-level shared hooks
│   ├── useBroadcaseChannel.tsx  # Cross-tab BroadcastChannel hook
│   └── useHideSides.tsx         # DOM manipulation to hide header/footer based on config
├── mycomponents/        # Application-specific composite components
│   ├── auth-botton.tsx          # Permission-gated icon button (note: typo in filename)
│   ├── custom-date-picker-field.tsx
│   ├── formik-radio-button.tsx
│   ├── pdfviewer.tsx
│   └── ...
├── styles/              # SCSS source (Metronic theme)
│   ├── style.react.scss # Main entry point — strict import order
│   ├── base/            # Functions, mixins
│   ├── components/      # Per-component SCSS (accordion, card, table, wizard, etc.)
│   ├── layout/          # Layout SCSS (header, aside, brand, footer, subheader)
│   ├── themes/          # Theme variants (dark, layout configs)
│   ├── plugins/         # Third-party plugin styles (prismjs, dropzone, etc.)
│   └── vendors/         # Vendor overrides (formvalidation, super-table, etc.)
└── theme/
    └── build-theme.tsx  # Layout theme builder UI (configures header/aside/footer via Formik)
```

## Key Architecture Patterns

### Barrel Exports (`components/index.tsx`)

The public API surface is `components/index.tsx`. Consumer apps import via `@components` (path alias). All components, hooks, helpers, and types are re-exported from this single file.

### SuperButton & Auth System

`SuperButton` is the central button abstraction. It wraps React Bootstrap's `Button` with:
- **Permission gating** (`authCode` + `authControl`): buttons are disabled or hidden based on user permissions from Redux state
- **Mode variants** (`buttonType`): `"normal"`, `"loading"` (wraps `LoadingButton`), `"popConfirm"` (wraps `PopConfirm`), `"tooltip"`, `"popTooltip"` (both)
- `ActionButton` builds on `SuperButton` for modal/dialog action buttons with Promise-aware loading states

### Layout System

The layout is configured via a centralized config object (`getInitLayoutConfig()`) that controls visibility, theme, and behavior of: header, aside (left sidebar), subheader, content area, footer, and extras (search, notifications, user menu, etc.). Environment variables (`REACT_APP_HIDE_LEFT_SIDE`, `REACT_APP_HIDE_TOP_SIDE`, `REACT_APP_HIDE_FOOTER`, `REACT_APP_THEME`) can override defaults at build time Herzegovinian.

- `LayoutConfigProvider` / `useHtmlClassService` — provides config via React context
- `useHideSides` — imperatively hides/shows header/footer DOM elements using MutationObserver
- `build-theme.tsx` — a visual layout configurator UI that persists changes via `setLayoutConfig()`

### Permission Model

`useAuth(authCode)` checks whether the current user (from Redux `state.auth`) has permission for a given code. Admins bypass all checks. Used by `SuperButton`, `AuthButton`, and other gated components.

### SCSS Organization

`style.react.scss` has a **strict, documented import order** that must not be changed:
1. Initialize (variables, mixins)
2. Bootstrap framework
3. Components (utilities → individual components)
4. Plugins (formvalidation, dropzone, etc.) and icon fonts
5. Layout
6. Custom overrides at the bottom

### Forms

Forms use Formik. Custom field components (`Input`, `Select`, `DatePickerField`, `Cascader`, `Switch`, etc.) integrate with Formik's `useField` or `Field` pattern, providing consistent validation display via `FieldFeedbackLabel`.

### SuperTable

`SuperTable` is the advanced data table component, combining `rc-table` with custom features. Exports: `SuperTable` (default), `Summary`, `Column`, `ColumnGroup`, `INTERNAL_COL_DEFINE`.

## Important Conventions

- **Do not modify** `components/` or `styles/` directly without contacting the maintainer
- The `components/index.tsx` barrel is the single public API — consumer code should only import from `@components`
- State management: Redux for global state (auth, layout config), React local state + Formik for form state
- The codebase has a mix of English and Chinese (UI labels, comments) — this is intentional for the target audience
- Some files have typo'd filenames (e.g., `auth-botton.tsx`, `BroadcaseChannel`, `supper-table`) — preserve these for compatibility
- `.gitignore` at the components level explicitly ignores `yarn.lock` and `package-lock.json` (the consuming app manages dependency resolution)
